package com.martinchooooooo.eyeseeyou.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.martinchooooooo.eyeseeyou.ui.common.BaseViewModel
import com.martinchooooooo.transportopendata.LibTransport
import com.martinchooooooo.transportopendata.liveTraffic.LiveTraffic
import com.martinchooooooo.transportopendata.store.Reactive
import io.reactivex.rxjava3.core.Scheduler

class HomeViewModel(
    private val handle: SavedStateHandle,
    private val libTranport: LibTransport,
    private val mainThread: Scheduler
) : BaseViewModel() {

    companion object {
        private const val LOG_TAG = "HomeViewModel"
    }

    val trafficCameras = MutableLiveData<List<LiveTraffic>>()

    init {
        libTranport.fetchLiveTraffic()

        libTranport.getLiveTraffic().observeOn(mainThread).subscribe({
            when(it) {
                is Reactive.Success -> {
                    trafficCameras.value = it.value
                }
                is Reactive.Error -> {

                }
            }
        }, {
            Log.e(LOG_TAG, "Unable to get our traffic data: ${it.message}")
        }).store()
    }

}