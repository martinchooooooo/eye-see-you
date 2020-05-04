package com.martinchooooooo.eyeseeyou.ui.liveTraffic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.martinchooooooo.eyeseeyou.R
import com.martinchooooooo.eyeseeyou.databinding.ItemLiveTrafficBinding
import com.martinchooooooo.eyeseeyou.ui.common.RoundedTransformation
import com.martinchooooooo.transportopendata.liveTraffic.LiveTraffic
import com.squareup.picasso.Picasso


class LiveTrafficAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: List<LiveTraffic>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LiveTrafficViewHolder(ItemLiveTrafficBinding.inflate(LayoutInflater.from(parent.context)))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as LiveTrafficViewHolder).bind(data!![position])
    }

    private class LiveTrafficViewHolder(private val binding: ItemLiveTrafficBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LiveTraffic) {
            binding.title.text = item.title
            Picasso.get()
                .load(item.href.toExternalForm())
                .resizeDimen(R.dimen.carousel_img_width, R.dimen.carousel_img_height)
                .transform(RoundedTransformation(radius = 20f, margin = 0f))
                .into(binding.imgLiveShot)
        }

    }


}