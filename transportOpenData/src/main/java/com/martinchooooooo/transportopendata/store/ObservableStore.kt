package com.martinchooooooo.transportopendata.store

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

class ObservableStore<T>(
    private val fetcher: () -> Single<T>,
    private val backgroundScheduler: Scheduler
) {

    private val internalDataStream = BehaviorSubject.create<Reactive<T>>()
    private val triggerFetch = PublishSubject.create<Unit>()
    private val disposables = CompositeDisposable()

    init {
        val fetcherDisposable = triggerFetch
            .switchMap { fetcher().subscribeOn(backgroundScheduler).toObservable() }
            .map { Reactive.Success(it) as Reactive<T> }
            .onErrorResumeNext { Observable.just(Reactive.Error<T>(it, internalDataStream.cachedValue())) }
            .doOnNext { internalDataStream.onNext(it) }
            .publish().connect()

        disposables.add(fetcherDisposable)
    }

    fun get(): Observable<Reactive<T>> = internalDataStream

    fun fetch() {
        if (internalDataStream.hasValue()) {

            val cachedValue = when (internalDataStream.value) {
                // If it's already loading, we don't want to trigger another load
                is Reactive.Loading -> return
                else -> internalDataStream.cachedValue()
            }
            internalDataStream.onNext(Reactive.Loading(cachedValue = cachedValue))
        } else {
            internalDataStream.onNext(Reactive.Loading(cachedValue = null))
        }
        triggerFetch.onNext(Unit)
    }

    fun teardown() {
        disposables.clear()
        internalDataStream.onComplete()
    }

    private fun BehaviorSubject<Reactive<T>>.cachedValue(): T? {
        return when (internalDataStream.value) {
            // If it's already loading, we don't want to trigger another load
            is Reactive.Loading -> (internalDataStream.value as Reactive.Loading).cachedValue
            is Reactive.Success -> (internalDataStream.value as Reactive.Success).value
            is Reactive.Error -> (internalDataStream.value as Reactive.Error).cachedValue
        }
    }

}

