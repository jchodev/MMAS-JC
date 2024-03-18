package com.jerryalberto.mmas.feature.home.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.jerryalberto.mmas.core.domain.usecase.GetCategoriesUseCase
import com.jerryalberto.mmas.core.model.data.Category

@HiltViewModel
class InputScreenViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    fun getCategories(): List<Category> {
        return getCategoriesUseCase()
    }

}