package com.jerryalberto.mmas.feature.home.ui.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerryalberto.mmas.core.common.result.Result
import com.jerryalberto.mmas.core.common.result.asResult
import com.jerryalberto.mmas.core.common.uistate.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.jerryalberto.mmas.core.domain.usecase.CategoriesUseCase
import com.jerryalberto.mmas.core.domain.usecase.TransactionUseCase
import com.jerryalberto.mmas.core.model.data.Category
import com.jerryalberto.mmas.core.model.data.TransactionType
import com.jerryalberto.mmas.feature.home.R
import com.jerryalberto.mmas.feature.home.ui.data.InputTransactionData

import dagger.hilt.android.qualifiers.ApplicationContext
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

    val uiState = savedStateHandle.getStateFlow(
        INPUT_DATA_STATE_KEY, UIState(data = InputTransactionData())
    )

    fun init(){
        savedStateHandle[INPUT_DATA_STATE_KEY] = UIState(data = InputTransactionData())
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
                data = uiState.value.data.copy(
                    transaction = uiState.value.data.transaction.copy(
                        description = description
                    )
                )
            )
        )
    }

    fun onDateSelected(date: Long){
        saveData(
            uiState = uiState.value.copy(
                data = uiState.value.data.copy(
                    transaction = uiState.value.data.transaction.copy(
                        date = date
                    )
                )
            )
        )
    }

    fun onTimeSelected(hour: Int, minute: Int){
        saveData(
            uiState = uiState.value.copy(
                data = uiState.value.data.copy(
                    transaction = uiState.value.data.transaction.copy(
                        hour = hour,
                        minute = minute,
                    )
                )
            )
        )
    }

    fun onCategorySelected(category: Category){
        saveData(
            uiState = uiState.value.copy(
                data = uiState.value.data.copy(
                    transaction = uiState.value.data.transaction.copy(
                        category = category
                    )
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
                data = uiState.value.data.copy(
                    transaction = uiState.value.data.transaction.copy(
                        amount = amount
                    ),
                    amountString = amountString,
                )
            )
        )
    }

    fun onSelectedUri(uri: Uri){
        saveData(
            uiState = uiState.value.copy(
                data = uiState.value.data.copy(
                    transaction = uiState.value.data.transaction.copy(
                        uri = if (uri == Uri.EMPTY){
                            ""
                        } else {
                            uri.toString()
                        }
                    )
                )
            )
        )
    }

    fun setTransactionType(transactionType: TransactionType){
        saveData(
            uiState = uiState.value.copy(
                data = uiState.value.data.copy(
                    transaction = uiState.value.data.transaction.copy(
                        type = transactionType
                    )
                )
            )
        )
    }

    private fun saveData(uiState: UIState<InputTransactionData>){
        savedStateHandle[INPUT_DATA_STATE_KEY] = uiState
    }

    fun saveTransaction(){
        //clear error
        saveData(
            uiState = uiState.value.copy(
                data = uiState.value.data.copy(
                    descriptionError = null,
                    amountError = null,
                    dateError = null,
                    timeError = null,
                    categoryError = null
                )
            )
        )

        //basic checking
        if (uiState.value.data.transaction.category == null){
            saveData(
                uiState = uiState.value.copy(
                    data = uiState.value.data.copy(
                        categoryError = context.getString(R.string.feature_home_error_field_require),
                    )
                )
            )
            return
        }
        if (uiState.value.data.transaction.date == null){
            saveData(
                uiState = uiState.value.copy(
                    data = uiState.value.data.copy(
                        dateError = context.getString(R.string.feature_home_error_field_require),
                    )
                )
            )
            return
        }
        if (uiState.value.data.transaction.hour == null){
            saveData(
                uiState = uiState.value.copy(
                    data = uiState.value.data.copy(
                        timeError = context.getString(R.string.feature_home_error_field_require),
                    )
                )
            )
            return
        }
        if (uiState.value.data.transaction.amount <= 0.0){
            saveData(
                uiState = uiState.value.copy(
                    data = uiState.value.data.copy(
                        amountError = context.getString(R.string.feature_home_error_field_require),
                    )
                )
            )
            return
        }

        viewModelScope.launch {
            transactionUseCase.insertTransaction( uiState.value.data.transaction)
                .asResult().collectLatest {result ->
                    when (result) {
                        is Result.Success -> {
                            saveData(
                                uiState = uiState.value.copy(
                                    loading = false,
                                    data = uiState.value.data.copy(
                                        isSuccess = true
                                    )
                                )
                            )
                        }
                        is Result.Loading -> {
                            saveData(
                                uiState = uiState.value.copy(
                                    loading = true
                                )
                            )
                        }
                        is Result.Error -> {
                            saveData(
                                uiState = uiState.value.copy(
                                    exception = result.exception
                                )
                            )
                        }
                    }
            }
        }
    }
}

