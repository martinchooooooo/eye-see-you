package com.martinchooooooo.transportopendata.liveTraffic

import com.martinchooooooo.transportopendata.TransportService
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import java.net.URL

internal class GetLiveTraffic(
    private val service: TransportService,
    private val backgroundScheduler: Scheduler
) : () -> Single<List<LiveTraffic>> {

    override fun invoke(): Single<List<LiveTraffic>> {
        return service.getLiveTrafficCameras().map { response ->
            response.features.map {
                LiveTraffic(
                    region = it.properties["region"] as String,
                    title = it.properties["title"] as String,
                    view = it.properties["view"] as String,
                    direction = it.properties["direction"] as String,
                    href = URL(it.properties["href"] as String)
                )
            }
        }
    }
}

