package com.martinchooooooo.transportopendata

import com.martinchooooooo.transportopendata.liveTraffic.LiveTraffic
import io.reactivex.rxjava3.core.Single

interface LibTransport {

    fun getLiveTraffic(): Single<List<LiveTraffic>>

}