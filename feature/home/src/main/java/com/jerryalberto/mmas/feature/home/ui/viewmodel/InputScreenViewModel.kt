package com.jerryalberto.mmas.feature.home.ui.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerryalberto.mmas.core.ui.ext.convertMillisToDate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.jerryalberto.mmas.core.domain.usecase.CategoriesUseCase
import com.jerryalberto.mmas.core.domain.usecase.TransactionUseCase
import com.jerryalberto.mmas.core.model.data.Category
import com.jerryalberto.mmas.core.model.data.TransactionType
import com.jerryalberto.mmas.feature.home.R
import com.jerryalberto.mmas.feature.home.ui.uistate.InputUiDataState

import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class InputScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val categoriesUseCase: CategoriesUseCase,
    private val transactionUseCase: TransactionUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val INPUT_STATE_KEY = "INPUT_STATE_KEY"

    val uiState = savedStateHandle.getStateFlow(
        INPUT_STATE_KEY, InputUiDataState()
    )

    private val _onSaved = MutableStateFlow<Boolean?>(null)
    val onSaved = _onSaved.asStateFlow()

    fun init(){
        savedStateHandle[INPUT_STATE_KEY] = InputUiDataState()
        _onSaved.value = null
    }

    fun getExpenseCategories(): List<Category> {
        return categoriesUseCase.getExpenseCategories()
    }

    fun getIncomeCategories(): List<Category> {
        return categoriesUseCase.getIncomeCategories()
    }

    fun onDescriptionChange(description: String){
        saveData(
            uiState = uiState.value.copy(
                transaction = uiState.value.transaction.copy(
                    description = description
                ),
            )
        )
    }

    fun onDateSelected(date: Long){
        saveData(
            uiState = uiState.value.copy(
                transaction = uiState.value.transaction.copy(
                    date = date
                )
            )
        )
    }

    fun onTimeSelected(hour: Int, minute: Int){
        saveData(
            uiState = uiState.value.copy(
                transaction = uiState.value.transaction.copy(
                    hour = hour,
                    minute = minute,
                )
            )
        )
    }

    fun onCategorySelected(category: Category){
        saveData(
            uiState = uiState.value.copy(
                transaction = uiState.value.transaction.copy(
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
            uiState = uiState.value.copy(
                transaction = uiState.value.transaction.copy(
                    amount = amount,
                ),
                amountString = amountString,
            )
        )
    }

    fun onSelectedUri(uri: Uri){
        saveData(
            uiState = uiState.value.copy(
                transaction = uiState.value.transaction.copy(
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
            uiState = uiState.value.copy(
                transaction = uiState.value.transaction.copy(
                    type = transactionType
                )
            )
        )
    }

    private fun saveData(uiState: InputUiDataState){
        savedStateHandle[INPUT_STATE_KEY] = uiState
    }

    fun saveTransaction(){
        //clear error
        saveData(
            uiState = uiState.value.copy(
                descriptionError = null,
                amountError = null,
                dateError = null,
                timeError = null,
                categoryError = null
            )
        )

        //basic checking
        if (uiState.value.transaction.category == null){
            saveData(
                uiState = uiState.value.copy(
                    categoryError = context.getString(R.string.feature_home_error_field_require)
                )
            )
            return
        }
        if (uiState.value.transaction.date == null){
            saveData(
                uiState = uiState.value.copy(
                    dateError = context.getString(R.string.feature_home_error_field_require)
                )
            )
            return
        }
        if (uiState.value.transaction.hour == null){
            saveData(
                uiState = uiState.value.copy(
                    timeError = context.getString(R.string.feature_home_error_field_require)
                )
            )
            return
        }
        if (uiState.value.transaction.amount!! <= 0.0){
            saveData(
                uiState = uiState.value.copy(
                    amountError = context.getString(R.string.feature_home_error_amount_greater_than_zero)
                )
            )
            return
        }

        viewModelScope.launch {
            transactionUseCase.insertTransaction(
                uiState.value.transaction
            )
            val list = transactionUseCase.getLatestTransaction().toList()
            list.forEach {
                Timber.d(it.toString())
            }

            saveData(
                uiState = InputUiDataState()
            )
            //_onSaved.emit(_onSaved.value?.not() ?: false)
        }
        _onSaved.value = _onSaved.value?.not() ?: false
    }

}