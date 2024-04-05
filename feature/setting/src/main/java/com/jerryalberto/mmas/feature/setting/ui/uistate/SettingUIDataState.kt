package com.jerryalberto.mmas.feature.setting.ui.uistate

import com.jerryalberto.mmas.core.model.data.Setting

data class SettingUIDataState(
    val loading: Boolean = false,

    val setting: Setting = Setting(),
)