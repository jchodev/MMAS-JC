package com.jerryalberto.mmas.feature.home.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerryalberto.mmas.core.designsystem.utils.convertMillisToDate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.jerryalberto.mmas.core.domain.usecase.CategoriesUseCase
import com.jerryalberto.mmas.core.domain.usecase.TransactionUseCase
import com.jerryalberto.mmas.core.model.data.TransactionType
import com.jerryalberto.mmas.feature.home.ui.helper.UiHelper
import com.jerryalberto.mmas.feature.home.model.CategoryDisplay
import com.jerryalberto.mmas.feature.home.ui.uistate.InputUiDataState
import com.jerryalberto.mmas.feature.home.ui.uistate.asTransaction
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class InputScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val categoriesUseCase: CategoriesUseCase,
    private val transactionUseCase: TransactionUseCase,
    private val uiHelper: UiHelper,
) : ViewModel() {

    private val INPUT_STATE_KEY = "INPUT_STATE_KEY"

    val uiState = savedStateHandle.getStateFlow(
        INPUT_STATE_KEY, InputUiDataState()
    )

    fun getCategories(): List<CategoryDisplay> {
        return categoriesUseCase.getExpenseCategory().map {
            uiHelper.categoryMapToDisplay(it)
        }
    }

    fun onDescriptionChange(description: String){
        saveData(
            uiState = uiState.value.copy(
                description = description
            )
        )
    }

    fun onDateSelected(date: Long){
        saveData(
            uiState = uiState.value.copy(
                dateString = date.convertMillisToDate(dateFormat = "dd/MM/yyyy"),
                date = date
            )
        )
    }

    fun onTimeSelected(hour: Int, minute: Int){
        saveData(
            uiState = uiState.value.copy(
                timeString = uiHelper.displayHourMinute(hour = hour, minute = minute),
                hour = hour,
                minute = minute,
            )
        )
    }

    fun onCategorySelected(category: CategoryDisplay){
        saveData(
            uiState = uiState.value.copy(
                category = category
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
            uiState = uiState.value.copy(
                amount = amount,
                amountString = amountString,
                amountFormatted = uiHelper.formatAmount(amount)
            )
        )
    }

    fun onSelectedUri(uri: Uri){
        saveData(
            uiState = uiState.value.copy(
                uri = if (uri == Uri.EMPTY){
                    ""
                } else {
                    uri.toString()
                }
            )
        )
    }

    fun setTransactionType(transactionType: TransactionType){
        saveData(
            uiState = uiState.value.copy(
                type = transactionType
            )
        )
    }

    private fun saveData(uiState: InputUiDataState){
        savedStateHandle[INPUT_STATE_KEY] = uiState
    }

    fun saveTransaction(){
        viewModelScope.launch {
            transactionUseCase.insertTransaction(
                uiState.value.asTransaction()
            )
            val list = transactionUseCase.getLatestTransaction().toList()
            list.forEach {
                Timber.d(it.toString())
            }
        }
    }
}