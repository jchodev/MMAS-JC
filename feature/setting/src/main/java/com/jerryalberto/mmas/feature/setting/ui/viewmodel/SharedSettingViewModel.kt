package com.jerryalberto.mmas.feature.setting.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.jerryalberto.mmas.core.model.data.Setting
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber


class SharedSettingViewModel(

): ViewModel() {

    private val _settingState = MutableStateFlow(Setting())
    val settingState = _settingState.asStateFlow()

    init {
        Timber.d("SharedSettingViewModel::init")
    }

}
