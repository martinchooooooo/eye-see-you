package com.martinchooooooo.cravings.ui

import androidx.lifecycle.SavedStateHandle
import com.martinchooooooo.cravings.ui.home.HomeViewModel
import com.martinchooooooo.transportopendata.LibTransport
import com.martinchooooooo.transportopendata.LibTransportImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val cravingsModule = module {

    single<LibTransport> { LibTransportImpl(
        backgroundScheduler = Schedulers.io()
    ) }

    val mainThread = named("mainThread")
    factory(mainThread) { AndroidSchedulers.mainThread() }

    viewModel { (handle: SavedStateHandle) -> HomeViewModel(handle, get(), get(mainThread)) }
}