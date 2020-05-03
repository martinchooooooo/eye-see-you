package com.martinchooooooo.cravings.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.martinchooooooo.cravings.ui.common.BaseViewModel
import com.martinchooooooo.transportopendata.LibTransport
import com.martinchooooooo.transportopendata.liveTraffic.LiveTraffic
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
        libTranport.getLiveTraffic().observeOn(mainThread).subscribe({
            trafficCameras.value = it
        }, {
            Log.e(LOG_TAG, "Unable to get our traffic data: ${it.message}")
        }).store()
    }

}