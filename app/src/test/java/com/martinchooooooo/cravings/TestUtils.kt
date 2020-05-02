package com.martinchooooooo.cravings

import io.reactivex.rxjava3.subjects.BehaviorSubject

fun <T> BehaviorSubject<T>.emitSingle(value: T) {
    onNext(value)
    onComplete()
}