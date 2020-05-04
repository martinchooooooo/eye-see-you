package com.martinchooooooo.transportopendata.store

sealed class Reactive<T> {

    data class Loading<T>(val cachedValue: T? = null) : Reactive<T>()

    data class Success<T>(val value: T) : Reactive<T>()

    data class Error<T>(val error: Throwable, val cachedValue: T? = null) : Reactive<T>()

}