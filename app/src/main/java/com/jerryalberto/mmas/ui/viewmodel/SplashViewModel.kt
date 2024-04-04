package com.jerryalberto.mmas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _readyStatue = MutableStateFlow<Boolean?>(null)
    val readyStatue = _readyStatue.asStateFlow()

    fun init() {
        viewModelScope.launch {
            delay(500)
            _readyStatue.value = true
        }
    }

}