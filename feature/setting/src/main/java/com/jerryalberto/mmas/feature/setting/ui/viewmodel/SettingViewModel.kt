package com.jerryalberto.mmas.feature.setting.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerryalberto.mmas.core.domain.usecase.SettingUseCase
import com.jerryalberto.mmas.core.model.data.CountryData
import com.jerryalberto.mmas.feature.setting.ui.uistate.SettingUIDataState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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

    fun getDateFormatList() : List<String> {
        return settingUseCase.getDateFormatList()
    }

    fun onCountryDataSelected(countryData: CountryData){
        updateUI(
            uiState = uiState.value.copy(
                setting = uiState.value.setting.copy(
                    countryData = countryData
                )
            )
        )
    }

    fun onDateFormatSelected(dateFormat: String){
        updateUI(
            uiState = uiState.value.copy(
                setting = uiState.value.setting.copy(
                    dateFormat = dateFormat
                )
            )
        )
    }

    private fun updateUI(uiState: SettingUIDataState){
        viewModelScope.launch {
            _uiState.value = uiState
            settingUseCase.saveSetting(uiState.setting)
        }
    }
}