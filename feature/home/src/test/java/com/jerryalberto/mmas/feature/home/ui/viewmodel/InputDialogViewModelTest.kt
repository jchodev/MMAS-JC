package com.jerryalberto.mmas.feature.home.ui.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.jerryalberto.mmas.core.domain.usecase.CategoriesUseCase
import com.jerryalberto.mmas.core.domain.usecase.TransactionUseCase
import com.jerryalberto.mmas.core.model.data.Category
import com.jerryalberto.mmas.core.model.data.CategoryType
import com.jerryalberto.mmas.core.model.data.TransactionType
import com.jerryalberto.mmas.feature.home.ui.data.InputTransactionDataState
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest

import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import timber.log.Timber

@OptIn(ExperimentalCoroutinesApi::class)
class InputDialogViewModelTest {

    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var categoriesUseCase: CategoriesUseCase
    private lateinit var transactionUseCase: TransactionUseCase
    private lateinit var context: Context
    private lateinit var viewModel: InputScreenViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        transactionUseCase = mockkClass(TransactionUseCase::class)
        categoriesUseCase = mockkClass(CategoriesUseCase::class)
        context = mockkClass(Context::class)
        savedStateHandle = SavedStateHandle()
        mockkStatic(Timber::class)

        viewModel = InputScreenViewModel(
            savedStateHandle = savedStateHandle,
            categoriesUseCase = categoriesUseCase,
            transactionUseCase = transactionUseCase,
            context = context
        )
    }

    @AfterEach
    fun after() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `test InputScreenViewModel init with success`() = runTest {
        viewModel.init()

        viewModel.uiState.test {
            Assertions.assertEquals(InputUiIState.Initial, awaitItem())
        }

        viewModel.dataState.test {
            val actual = awaitItem()
            Assertions.assertEquals(InputTransactionDataState().descriptionError, actual.descriptionError)
            Assertions.assertEquals(InputTransactionDataState().amountString, actual.amountString)
            Assertions.assertEquals(InputTransactionDataState().amountError, actual.amountError)
            Assertions.assertEquals(InputTransactionDataState().dateError, actual.dateError)
            Assertions.assertEquals(InputTransactionDataState().timeError, actual.timeError)
            Assertions.assertEquals(InputTransactionDataState().categoryError, actual.categoryError)
        }
    }

    @Test
    fun `test InputScreenViewModel getExpenseCategories with success`() {
        //assign
        every {
            categoriesUseCase.getExpenseCategories()
        } returns emptyList()

        //action
        val actual = viewModel.getExpenseCategories()

        //verify
        Assertions.assertEquals(0, actual.size)
    }

    @Test
    fun `test InputScreenViewModel getIncomeCategories with success`() {
        //assign
        every {
            categoriesUseCase.getIncomeCategories()
        } returns emptyList()

        //action
        val actual = viewModel.getIncomeCategories()

        //verify
        Assertions.assertEquals(0, actual.size)
    }

    @Test
    fun `test InputScreenViewModel onDescriptionChange`() = runTest {
        val newValue = "newDesc"
        viewModel.onDescriptionChange(newValue)
        viewModel.dataState.test {
            val actual = awaitItem()
            Assertions.assertEquals(newValue, actual.transaction.description)
        }
    }

    @Test
    fun `test InputScreenViewModel onDateSelected`() = runTest {
        val newValue = 1L
        viewModel.onDateSelected(newValue)
        viewModel.dataState.test {
            val actual = awaitItem()
            Assertions.assertEquals(newValue, actual.transaction.date)
        }
    }

    @Test
    fun `test InputScreenViewModel onTimeSelected`() = runTest {
        val newValue = 1
        viewModel.onTimeSelected(newValue, newValue)
        viewModel.dataState.test {
            val actual = awaitItem()

            Assertions.assertEquals(newValue, actual.transaction.hour)
            Assertions.assertEquals(newValue, actual.transaction.minute)
        }
    }

    @Test
    fun `test InputScreenViewModel onCategorySelected`() = runTest {
        val newValue = Category(
            CategoryType.SALARY
        )
        viewModel.onCategorySelected(newValue)
        viewModel.dataState.test {
            val actual = awaitItem()
            Assertions.assertEquals(newValue, actual.transaction.category)
        }
    }

    @Test
    fun `test InputScreenViewModel onAmountChange`() = runTest {
        val newValue = "100.00"
        viewModel.onAmountChange(newValue)
        viewModel.dataState.test {
            val actual = awaitItem()
            Assertions.assertEquals(newValue, actual.amountString)
            Assertions.assertEquals(newValue.toDouble(), actual.transaction.amount)
        }
    }


    @Test
    fun `test InputScreenViewModel onSelectedUri`() = runTest {
        val newValue = mockkClass(Uri::class)
        viewModel.onSelectedUri(newValue)
        viewModel.dataState.test {
            val actual = awaitItem()
            Assertions.assertEquals(newValue.toString(), actual.transaction.uri)
        }
    }

    @Test
    fun `test InputScreenViewModel setTransactionType`() = runTest {
        val newValue = TransactionType.INCOME
        viewModel.setTransactionType(newValue)
        viewModel.dataState.test {
            val actual = awaitItem()
            Assertions.assertEquals(newValue, actual.transaction.type)
        }
    }
}