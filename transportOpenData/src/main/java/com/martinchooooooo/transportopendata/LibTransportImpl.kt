package com.martinchooooooo.transportopendata

import com.martinchooooooo.transportopendata.liveTraffic.GetLiveTraffic
import com.martinchooooooo.transportopendata.liveTraffic.LiveTraffic
import com.martinchooooooo.transportopendata.store.ObservableStore
import com.martinchooooooo.transportopendata.store.Reactive
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.URL


class LibTransportImpl(
    url: URL = URL("https://api.transport.nsw.gov.au"),
    apiKey: String = "",
    private val backgroundScheduler: Scheduler
) : LibTransport {

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

    /** App lifecycle stores **/
    private val lifeTrafficStore by lazy {
        ObservableStore(
            fetcher = GetLiveTraffic(service),
            backgroundScheduler = backgroundScheduler
        )
    }


    /** Exposed API **/

    override fun fetchLiveTraffic() = lifeTrafficStore.fetch()

    override fun getLiveTraffic(): Observable<Reactive<List<LiveTraffic>>> = lifeTrafficStore.get()

}

