package com.jerryalberto.mmas.feature.setting.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.jerryalberto.mmas.core.domain.usecase.SettingUseCase
import com.jerryalberto.mmas.core.model.data.CountryData
import com.jerryalberto.mmas.feature.setting.ui.uistate.SettingUIDataState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingUseCase: SettingUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingUIDataState())
    val uiState = _uiState.asStateFlow()

    fun getCountryList() : List<CountryData> {
        return settingUseCase.getCountryList()
    }

    fun onCountryDataSelected(countryData: CountryData){
        updateUI(
            uiState = uiState.value.copy(
                loading = false,
                countryData = countryData
            )
        )
    }

    private fun updateUI(uiState: SettingUIDataState){
        _uiState.value = uiState
    }
}