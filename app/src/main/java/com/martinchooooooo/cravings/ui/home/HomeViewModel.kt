package com.martinchooooooo.cravings.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class HomeViewModel(
    private val handle: SavedStateHandle
): ViewModel() {

    companion object {
        private const val KEY_MESSAGE = "KEY_MESSAGE"
    }

    val header: MutableLiveData<String> = handle.getLiveData<String>(KEY_MESSAGE)

    init {
        header.value = "Hello World"
    }

}