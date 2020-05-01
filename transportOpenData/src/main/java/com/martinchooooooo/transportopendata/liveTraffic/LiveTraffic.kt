package com.martinchooooooo.transportopendata.liveTraffic

import java.net.URL

data class LiveTraffic(
    val region: String,
    val title: String,
    val view: String,
    val direction: String,
    val href: URL
)