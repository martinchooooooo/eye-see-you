package com.martinchooooooo.transportopendata.liveTraffic

import com.martinchooooooo.transportopendata.LibTransportImpl
import com.martinchooooooo.transportopendata.`responds with`
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.mockwebserver.MockWebServer
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.net.URL

internal class GetLiveTrafficSpec {

    lateinit var mockServer: MockWebServer
    lateinit var libImpl: LibTransportImpl

    @BeforeEach
    fun setup() {
        mockServer = MockWebServer()
        libImpl = LibTransportImpl(url = mockServer.url("/"), backgroundScheduler = Schedulers.trampoline())
    }

    @Test
    fun `maps json to appropriate entity`() {
        // given
        mockServer `responds with` jsonResponse

        // when
        val subscriber = libImpl.getLiveTraffic().test()
        // then
        with(subscriber) {
            assertComplete()
            values()[0] shouldBeEqualTo listOf(
                LiveTraffic(
                    region = "SYD_SOUTH",
                    title = "5 Ways (Miranda)",
                    view = "5 ways at The Boulevarde looking west towards Sutherland.",
                    direction = "W",
                    href = URL("https://www.rms.nsw.gov.au/trafficreports/cameras/camera_images/5ways.jpg")
                )
            )
        }
    }


    val jsonResponse = """
{
      "type": "FeatureCollection",
      "rights": {
        "copyright": "Transport for NSW",
        "licence": "https://www.livetraffic.com/#dev"
      },
      "features": [
        {
          "type": "Feature",
          "id": "d2e386",
          "geometry": {
            "type": "Point",
            "coordinates": [
              151.10533,
              -34.02977
            ]
          },
          "properties": {
            "region": "SYD_SOUTH",
            "title": "5 Ways (Miranda)",
            "view": "5 ways at The Boulevarde looking west towards Sutherland.",
            "direction": "W",
            "href": "https://www.rms.nsw.gov.au/trafficreports/cameras/camera_images/5ways.jpg"
          }
        }
      ]
  }
        """.trimIndent()

}