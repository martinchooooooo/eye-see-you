package com.martinchooooooo.eyeseeyou.ui.common

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

open class BaseViewModel: ViewModel() {

    private val subscriptions = CompositeDisposable()

    protected fun Disposable.store() {
        subscriptions.add(this)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }
}