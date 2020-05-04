package com.martinchooooooo.transportopendata.store

import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.SingleSubject
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ObservableStoreSpec {

    private lateinit var store: ObservableStore<String>
    private val fetcher: () -> Single<String> = mockk()

    @BeforeEach
    fun setup() {
        clearMocks(fetcher)
        store = ObservableStore(fetcher, Schedulers.trampoline())
    }

    @Test
    fun `get() - subscribing to it without calling fetch() emits no initial values`() {
        // when
        val subscriber = store.get().test()
        // then
        with(subscriber) {
            assertNotComplete()
            values().size shouldBe 0
        }
    }

    @Test
    fun `get() - emits Loading then first value returned by fetcher when fetch() is called`() {
        // given we have a subscriber
        val subscriber = store.get().test()
        val fetcherStream = SingleSubject.create<String>()
        every { fetcher() } returns fetcherStream
        // when
        store.fetch()
        // then
        subscriber.values()[0] shouldBeEqualTo Reactive.Loading(null)

        // when fetcher finishes
        val response = "foo"
        fetcherStream.onSuccess(response)
        // then
        subscriber.values()[1] shouldBeEqualTo Reactive.Success(response)
        subscriber.assertNotComplete()
    }

    @Test
    fun `calling fetch() a second time puts first response into cachedValue field`() {
        // given we have a subscriber
        val subscriber = store.get().test()
        // and our first response is successful
        val firstVal = "foo"
        every { fetcher() } returns Single.just(firstVal)
        store.fetch()

        // when our second fetch returns new data
        val secondVal = "bar"
        every { fetcher() } returns Single.just(secondVal)
        store.fetch()

        // then
        with(subscriber) {
            assertNotComplete()

            values()[0] shouldBeEqualTo Reactive.Loading(cachedValue = null)
            values()[1] shouldBeEqualTo Reactive.Success(firstVal)
            values()[2] shouldBeEqualTo Reactive.Loading(cachedValue = firstVal)
            values()[3] shouldBeEqualTo Reactive.Success(secondVal)
        }
    }

    @Test
    fun `error handling - emits previous value when first fetch was successfull but second one failed`() {
        // given we have a subscriber
        val subscriber = store.get().test()
        // and our first response is successful
        val firstVal = "foo"
        every { fetcher() } returns Single.just(firstVal)
        store.fetch()

        // when our second fetch is an error
        val err = RuntimeException("oops")
        every { fetcher() } returns Single.error(err)
        store.fetch()

        // then
        with(subscriber) {
            assertNotComplete()

            values()[0] shouldBeEqualTo Reactive.Loading(cachedValue = null)
            values()[1] shouldBeEqualTo Reactive.Success(firstVal)
            values()[2] shouldBeEqualTo Reactive.Loading(cachedValue = firstVal)
            values()[3] shouldBeEqualTo Reactive.Error(error = err, cachedValue = firstVal)
        }
    }

    @Test
    fun `teardown() - cleans up internal subscriptions, and completes all subscribers, thus no more network calls will be cached`() {
        // given we have a subscriber and we've fetched some data
        val subscriber = store.get().test()
        every { fetcher() } returns Single.just("foo")
        store.fetch()

        // when
        store.teardown()

        // then
        with(subscriber) {
            assertComplete()

            values()[0] shouldBeEqualTo Reactive.Loading(cachedValue = null)
            values()[1] shouldBeEqualTo Reactive.Success("foo")
        }

    }

}