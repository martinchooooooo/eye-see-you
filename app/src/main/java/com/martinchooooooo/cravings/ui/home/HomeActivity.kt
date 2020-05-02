package com.martinchooooooo.cravings.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.martinchooooooo.cravings.databinding.ActivityHomeBinding
import com.martinchooooooo.cravings.databinding.ItemLiveTrafficBinding
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by stateViewModel()

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bind()
    }

    private fun bind() {
        viewModel.trafficCameras.observe(this, Observer {
            binding.liveTrafficRecycler.setItems(it)
        })

    }

}
