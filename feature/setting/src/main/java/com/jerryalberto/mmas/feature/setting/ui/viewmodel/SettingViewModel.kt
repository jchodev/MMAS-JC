package com.jerryalberto.mmas.feature.setting.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerryalberto.mmas.core.common.result.Result
import com.jerryalberto.mmas.core.common.result.asResult
import com.jerryalberto.mmas.core.domain.usecase.SettingUseCase
import com.jerryalberto.mmas.core.model.data.CountryData
import com.jerryalberto.mmas.core.model.data.Setting
import com.jerryalberto.mmas.core.ui.ext.toCountryData
import com.jerryalberto.mmas.feature.setting.ui.uistate.SettingUIDataState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingUseCase: SettingUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingUIDataState())
    val uiState = _uiState.asStateFlow()

    private val _settingState = MutableStateFlow(Setting())
    val settingState = _settingState.asStateFlow()

    init {
        Timber.d("SettingViewModel::init!!")
        viewModelScope.launch {
            settingUseCase.getSetting().asResult().collectLatest {
                when (it) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        Timber.d("SettingViewModel::success!!")

                        updateUI (
                            uiState = uiState.value.copy(
                                loading = false,
                                setting = it.data
                            )
                        )
                    }
                    else -> {
                        //TODO
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun showLoading(show: Boolean){
        updateUI (
            uiState = uiState.value.copy(
                loading = show
            )
        )
    }


    fun getCountryList() : List<CountryData> {
        return settingUseCase.getCountryList().map{
            it.toCountryData()
        }
    }

    fun getDateFormatList() : List<String> {
        return settingUseCase.getDateFormatList()
    }

    fun onCountryDataSelected(countryCode: String){
        updateUI(
            uiState = uiState.value.copy(
                setting = uiState.value.setting.copy(
                    countryCode = countryCode
                )
            )
        )
        saveTransaction()
    }

    fun onDateFormatSelected(dateFormat: String){
        updateUI(
            uiState = uiState.value.copy(
                setting = uiState.value.setting.copy(
                    dateFormat = dateFormat
                )
            )
        )
        saveTransaction()
    }

    fun onThemeSelected(theme: String){
        updateUI(
            uiState = uiState.value.copy(
                setting = uiState.value.setting.copy(
                    theme = theme
                )
            )
        )
        saveTransaction()
    }

    private fun updateUI(uiState: SettingUIDataState){
        _uiState.value = uiState
        _settingState.value = uiState.setting
    }

    private fun saveTransaction(){
        viewModelScope.launch {
            settingUseCase.saveSetting(_uiState.value.setting)
        }
    }

}