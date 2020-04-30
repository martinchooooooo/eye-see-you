package com.martinchooooooo.cravings.ui.home

import androidx.lifecycle.SavedStateHandle
import com.martinchooooooo.cravings.InstantExecutorExtension
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
class HomeViewModelSpec {

    lateinit var vm: HomeViewModel
    val saveStateHandle = SavedStateHandle()

    @BeforeEach
    fun setup() {
        vm = HomeViewModel(saveStateHandle)
    }

    @Test
    fun `header is available when created`() {
        // given a view model has already been created
        // then
        vm.header.value shouldBeEqualTo "Hello World"
    }

}