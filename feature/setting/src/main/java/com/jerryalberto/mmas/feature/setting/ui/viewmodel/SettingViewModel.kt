package com.jerryalberto.mmas.feature.setting.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerryalberto.mmas.core.common.result.Result
import com.jerryalberto.mmas.core.common.result.asResult
import com.jerryalberto.mmas.core.domain.usecase.SettingUseCase
import com.jerryalberto.mmas.core.domain.usecase.TransactionUseCase
import com.jerryalberto.mmas.core.model.data.CountryData
import com.jerryalberto.mmas.core.model.data.Setting
import com.jerryalberto.mmas.core.model.data.ThemeType
import com.jerryalberto.mmas.core.model.data.TimeFormatType
import com.jerryalberto.mmas.core.ui.ext.toCountryData


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingUseCase: SettingUseCase,
    private val transactionUseCase: TransactionUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SettingUIState>(SettingUIState.Initial)
    val uiState = _uiState.asStateFlow()

    private val _fetchSettingState = MutableStateFlow<FetchSettingDataState>(FetchSettingDataState.Loading)
    val fetchSettingState = _fetchSettingState.asStateFlow()

    private val _settingState = MutableStateFlow(Setting())
    val settingState = _settingState.asStateFlow()

    init {
        Timber.d("SettingViewModel::init")
        //fetchSetting()
    }

    fun fetchSetting(){
        Timber.d("SettingViewModel::fetchSetting")
        viewModelScope.launch {
            settingUseCase.getSetting().asResult().collectLatest {result->
                Timber.d("SettingViewModel::result:${result}")
                _fetchSettingState.value = when (result) {
                    is Result.Success -> {
                        _settingState.value = result.data
                        FetchSettingDataState.Success
                    }
                    is Result.Loading -> FetchSettingDataState.Loading
                    is Result.Error -> FetchSettingDataState.Error(exception = result.exception)
                }
            }
        }
    }

    fun clearData(){
        Timber.d("SettingViewModel::clearData")
        viewModelScope.launch {
            transactionUseCase.deleteAllTransaction().asResult().collectLatest {result->
                _uiState.value = when (result) {
                    is Result.Success -> {
                        resultUiState()
                        SettingUIState.Success
                    }
                    is Result.Loading -> SettingUIState.Loading
                    is Result.Error -> SettingUIState.Error(exception = result.exception)
                }
            }
        }
    }

    private fun resultUiState(){
        viewModelScope.launch {
            delay(500)
            _uiState.value = SettingUIState.Initial
        }
    }

    fun getCountryList() : List<CountryData> {
        return settingUseCase.getCountryList().map{
            it.toCountryData()
        }
    }

    fun getDateFormatList() : List<String> {
        return settingUseCase.getDateFormatList()
    }

    fun getTimeFormatList() : List<TimeFormatType> {
        return settingUseCase.getTimeFormatList()
    }

    fun getThemeList(): List<ThemeType> {
        return settingUseCase.getThemeList()
    }

    fun onCountryDataSelected(countryCode: String){
        _settingState.value = settingState.value.copy(
            countryCode = countryCode
        )
        saveTransaction()
    }

    fun onDateFormatSelected(dateFormat: String){
        _settingState.value = settingState.value.copy(
            dateFormat = dateFormat
        )
        saveTransaction()
    }
    fun onTimeFormatSelected(timeFormatType: TimeFormatType){
        _settingState.value = settingState.value.copy(
            timeFormatType = timeFormatType
        )
        saveTransaction()
    }

    fun onThemeSelected(themeType: ThemeType){
        _settingState.value = settingState.value.copy(
            themeType = themeType
        )
        saveTransaction()
    }


    private fun saveTransaction(){
        viewModelScope.launch {
            settingUseCase.saveSetting(settingState.value)
        }
    }

}

sealed interface FetchSettingDataState {
    data object Loading : FetchSettingDataState
    data object Success : FetchSettingDataState
    data class Error(val exception: Throwable) : FetchSettingDataState
}

sealed interface SettingUIState {
    data object Initial : SettingUIState
    data object Loading : SettingUIState
    data object Success : SettingUIState
    data class Error(val exception: Throwable) : SettingUIState
}
