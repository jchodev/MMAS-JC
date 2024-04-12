package com.jerryalberto.mmas.feature.home.ui.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerryalberto.mmas.core.common.result.Result
import com.jerryalberto.mmas.core.common.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.jerryalberto.mmas.core.domain.usecase.CategoriesUseCase
import com.jerryalberto.mmas.core.domain.usecase.TransactionUseCase
import com.jerryalberto.mmas.core.model.data.Category
import com.jerryalberto.mmas.core.model.data.TransactionType
import com.jerryalberto.mmas.feature.home.R
import com.jerryalberto.mmas.feature.home.ui.data.InputTransactionDataState

import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class InputDialogViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val categoriesUseCase: CategoriesUseCase,
    private val transactionUseCase: TransactionUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val INPUT_DATA_STATE_KEY = "INPUT_DATA_STATE_KEY"

    private val _uiState = MutableStateFlow<InputUiIState>(InputUiIState.Initial)
    val uiState = _uiState.asStateFlow()

    val dataState = savedStateHandle.getStateFlow(
        INPUT_DATA_STATE_KEY, InputTransactionDataState()
    )

    fun init(){
        savedStateHandle[INPUT_DATA_STATE_KEY] = InputTransactionDataState()
        _uiState.value = InputUiIState.Initial
    }

    fun getExpenseCategories(): List<Category> {
        return categoriesUseCase.getExpenseCategories()
    }

    fun getIncomeCategories(): List<Category> {
        return categoriesUseCase.getIncomeCategories()
    }

    fun onDescriptionChange(description: String){
        saveData(
            uiState = dataState.value.copy(
                transaction = dataState.value.transaction.copy(
                    description = description
                ),
            )
        )
    }

    fun onDateSelected(date: Long){
        saveData(
            uiState = dataState.value.copy(
                transaction = dataState.value.transaction.copy(
                    date = date
                )
            )
        )
    }

    fun onTimeSelected(hour: Int, minute: Int){
        saveData(
            uiState = dataState.value.copy(
                transaction = dataState.value.transaction.copy(
                    hour = hour,
                    minute = minute,
                )
            )
        )
    }

    fun onCategorySelected(category: Category){
        saveData(
            uiState = dataState.value.copy(
                transaction = dataState.value.transaction.copy(
                    category = category
                )
            )
        )
    }

    fun onAmountChange(amountString: String){
        var amount = 0.0
        try {
            amount = amountString.toDouble()
        } catch (exception: Exception){

        }
        saveData(
            uiState = dataState.value.copy(
                transaction = dataState.value.transaction.copy(
                    amount = amount,
                ),
                amountString = amountString,
            )
        )
    }

    fun onSelectedUri(uri: Uri){
        saveData(
            uiState = dataState.value.copy(
                transaction = dataState.value.transaction.copy(
                    uri = if (uri == Uri.EMPTY){
                        ""
                    } else {
                        uri.toString()
                    }
                )
            )
        )
    }

    fun setTransactionType(transactionType: TransactionType){
        saveData(
            uiState = dataState.value.copy(
                transaction = dataState.value.transaction.copy(
                    type = transactionType
                )
            )
        )
    }

    private fun saveData(uiState: InputTransactionDataState){
        savedStateHandle[INPUT_DATA_STATE_KEY] = uiState
    }

    fun saveTransaction(){
        //clear error
        saveData(
            uiState = dataState.value.copy(
                descriptionError = null,
                amountError = null,
                dateError = null,
                timeError = null,
                categoryError = null
            )
        )

        //basic checking
        if (dataState.value.transaction.category == null){
            saveData(
                uiState = dataState.value.copy(
                    categoryError = context.getString(R.string.feature_home_error_field_require)
                )
            )
            return
        }
        if (dataState.value.transaction.date == null){
            saveData(
                uiState = dataState.value.copy(
                    dateError = context.getString(R.string.feature_home_error_field_require)
                )
            )
            return
        }
        if (dataState.value.transaction.hour == null){
            saveData(
                uiState = dataState.value.copy(
                    timeError = context.getString(R.string.feature_home_error_field_require)
                )
            )
            return
        }
        if (dataState.value.transaction.amount!! <= 0.0){
            saveData(
                uiState = dataState.value.copy(
                    amountError = context.getString(R.string.feature_home_error_amount_greater_than_zero)
                )
            )
            return
        }

        viewModelScope.launch {
            transactionUseCase.insertTransaction(dataState.value.transaction)
                .asResult().collectLatest {result ->
                    _uiState.value = when (result) {
                        is Result.Success -> InputUiIState.Success
                        is Result.Loading -> InputUiIState.Loading
                        is Result.Error -> InputUiIState.Error(exception = result.exception)
                    }
            }
        }
    }
}

sealed interface InputUiIState {
    data object Initial : InputUiIState
    data object Loading : InputUiIState
    data object Success : InputUiIState
    data class Error(val exception: Throwable) : InputUiIState
}
