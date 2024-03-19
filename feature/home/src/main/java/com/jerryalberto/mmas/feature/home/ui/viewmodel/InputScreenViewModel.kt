package com.jerryalberto.mmas.feature.home.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jerryalberto.mmas.core.designsystem.utils.convertMillisToDate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.jerryalberto.mmas.core.domain.usecase.CategoriesUseCase
import com.jerryalberto.mmas.core.model.data.Category
import com.jerryalberto.mmas.feature.home.helper.CategoryHelper
import com.jerryalberto.mmas.feature.home.model.CategoryDisplay
import com.jerryalberto.mmas.feature.home.ui.uistate.InputUiDataState

@HiltViewModel
class InputScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val categoriesUseCase: CategoriesUseCase,
    private val categoryHelper: CategoryHelper,
) : ViewModel() {

    private val INPUT_STATE_KEY = "INPUT_STATE_KEY"

    val uiState = savedStateHandle.getStateFlow(
        INPUT_STATE_KEY, InputUiDataState()
    )

    fun getCategories(): List<CategoryDisplay> {
        return categoriesUseCase.getExpenseCategory().map {
            categoryHelper.mapToDisplay(it)
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
                timeString = "$hour:$minute",
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

    fun onAmountChange(amount: String){
        saveData(
            uiState = uiState.value.copy(
                amount = amount.toDouble(),
                amountString = amount
            )
        )
    }

    private fun saveData(uiState: InputUiDataState){
        savedStateHandle[INPUT_STATE_KEY] = uiState
    }

}