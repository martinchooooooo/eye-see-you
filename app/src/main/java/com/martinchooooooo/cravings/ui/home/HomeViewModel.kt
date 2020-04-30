package com.martinchooooooo.cravings.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class HomeViewModel(
    private val handle: SavedStateHandle
): ViewModel() {

    val header = MutableLiveData<String>()

    init {
        header.value = "Hello World"
    }

}