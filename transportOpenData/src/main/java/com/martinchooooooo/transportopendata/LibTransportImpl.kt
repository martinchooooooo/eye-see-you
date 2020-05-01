package com.martinchooooooo.transportopendata

import com.martinchooooooo.transportopendata.liveTraffic.GetLiveTraffic
import com.martinchooooooo.transportopendata.liveTraffic.LiveTraffic
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class LibTransportImpl(
    url: HttpUrl = "https://api.transport.nsw.gov.au".toHttpUrl(),
    apiKey: String = "",
    private val backgroundScheduler: Scheduler
): LibTransport {

    private var okHttpClient = OkHttpClient().newBuilder().addInterceptor {
        val newRequest = it.request()
            .newBuilder()
            .header("Authorization", "apikey $apiKey")
            .build()

        it.proceed(newRequest)
    }.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    private val service = retrofit.create(TransportService::class.java)

    override fun getLiveTraffic(): Single<List<LiveTraffic>> {
        return GetLiveTraffic(service, backgroundScheduler)().onBackground()
    }

    private fun <T> Single<T>.onBackground() = this.subscribeOn(backgroundScheduler)

}

