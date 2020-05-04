package com.martinchooooooo.eyeseeyou.ui.liveTraffic

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.martinchooooooo.transportopendata.liveTraffic.LiveTraffic

class LiveTrafficCarousel : RecyclerView {

    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet?) : super(ctx, attrs)
    constructor(ctx: Context, attrs: AttributeSet?, attrSetId: Int) : super(ctx, attrs, attrSetId)

    private val trafficAdapter = LiveTrafficAdapter()

    fun setItems(items: List<LiveTraffic>?) { trafficAdapter.data = items }

    init {
        adapter = trafficAdapter
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

}