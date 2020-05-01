package com.martinchooooooo.transportopendata

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

infix fun MockWebServer.`responds with`(res: String) {
    val response = MockResponse()
        .setResponseCode(200)
        .setBody(res)
    this.enqueue(response)
}