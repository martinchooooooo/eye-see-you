package com.martinchooooooo.transportopendata

import com.martinchooooooo.transportopendata.liveTraffic.LiveTraffic
import com.martinchooooooo.transportopendata.store.Reactive
import io.reactivex.rxjava3.core.Observable

interface LibTransport {

    fun fetchLiveTraffic()
    fun getLiveTraffic(): Observable<Reactive<List<LiveTraffic>>>

}