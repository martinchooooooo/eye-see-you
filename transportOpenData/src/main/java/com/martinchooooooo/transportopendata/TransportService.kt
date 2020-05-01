package com.martinchooooooo.transportopendata

import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.http.GET

interface TransportService {

    @GET("/v1/live/cameras")
    fun getLiveTrafficCameras(): Single<FeatureCollectionEntity>

}