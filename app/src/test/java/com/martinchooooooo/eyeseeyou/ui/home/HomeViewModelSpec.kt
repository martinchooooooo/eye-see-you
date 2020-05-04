package com.martinchooooooo.eyeseeyou.ui.home

import androidx.lifecycle.SavedStateHandle
import com.martinchooooooo.eyeseeyou.InstantExecutorExtension
import com.martinchooooooo.transportopendata.LibTransport
import com.martinchooooooo.transportopendata.liveTraffic.LiveTraffic
import com.martinchooooooo.transportopendata.store.Reactive
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.amshove.kluent.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.net.URL

@ExtendWith(InstantExecutorExtension::class)
class HomeViewModelSpec {

    private lateinit var vm: HomeViewModel
    private val saveStateHandle = SavedStateHandle()
    private val lib: LibTransport = mockk(relaxUnitFun = true)

    private lateinit var liveTrafficStream: BehaviorSubject<Reactive<List<LiveTraffic>>>

    @BeforeEach
    fun setup() {
        liveTrafficStream = BehaviorSubject.create()
        every { lib.getLiveTraffic() } returns liveTrafficStream.hide()

        vm = HomeViewModel(saveStateHandle, lib, Schedulers.trampoline())
    }

    @Test
    fun `live traffic from lib is stored in liveTraffic property`() {
        // given live traffic info
        val traffic = listOf(
            LiveTraffic(
                region = "Sydney",
                href = URL("http://www.example.com"),
                direction = "W",
                title = "Some lights",
                view = "Perfect"
            )
        )
        // when
        liveTrafficStream.onNext(Reactive.Success(traffic))
        // then
        vm.trafficCameras.value shouldBe traffic
        verify(exactly = 1) { lib.fetchLiveTraffic() }
    }

}