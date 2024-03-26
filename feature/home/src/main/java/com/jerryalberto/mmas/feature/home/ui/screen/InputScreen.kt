package com.jerryalberto.mmas.feature.home.ui.screen

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.AvTimer
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold

import androidx.compose.material3.Text


import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType

import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import com.jerryalberto.mmas.core.designsystem.button.MmasButton
import com.jerryalberto.mmas.core.designsystem.dialog.DatePickerPromptDialog
import com.jerryalberto.mmas.core.designsystem.dialog.TimePickerPromptDialog
import com.jerryalberto.mmas.core.designsystem.constant.ColorConstant

import com.jerryalberto.mmas.core.designsystem.edittext.MmasTextEdit
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.core.designsystem.topbar.MmaTopBar
import com.jerryalberto.mmas.core.designsystem.utils.CurrencyAmountInputVisualTransformation
import com.jerryalberto.mmas.core.model.data.TransactionType
import com.jerryalberto.mmas.feature.home.R
import com.jerryalberto.mmas.feature.home.model.CategoryDisplay
import com.jerryalberto.mmas.feature.home.ui.component.AddAttachmentRow
import com.jerryalberto.mmas.feature.home.ui.uistate.InputUiDataState
import com.jerryalberto.mmas.feature.home.ui.viewmodel.InputScreenViewModel
import com.jerryalberto.mmas.feature.home.ui.component.CategorySelectDialog

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InputScreen(
    viewModel: InputScreenViewModel,
    onTopBarLeftClick: () -> Unit = {},
) {

    InputScreenContent(
        state = viewModel.uiState.collectAsState().value,
        onTopBarLeftClick = onTopBarLeftClick,
        categories = viewModel.getCategories(),
        onDescriptionChange = {
            viewModel.onDescriptionChange(it)
        },
        onDateSelected = {
            viewModel.onDateSelected(it)
        },
        onTimeSelected = { hour, minute ->
            viewModel.onTimeSelected(hour = hour, minute = minute)
        },
        onCategorySelected = {
            viewModel.onCategorySelected(it)
        },
        onAmountChange = {
            viewModel.onAmountChange(it)
        },
        onSaveClick = {
            viewModel.saveTransaction()
        },
        onSelectedUri = {
            viewModel.onSelectedUri(it)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputScreenContent(
    state: InputUiDataState = InputUiDataState(),
    onTopBarLeftClick: () -> Unit = {},
    categories: List<CategoryDisplay> = listOf(),
    onDescriptionChange: (String)-> Unit = {},
    onDateSelected: (Long) -> Unit = {},
    onTimeSelected: (Int, Int) -> Unit = {
            hour, minute ->
    },
    onCategorySelected: (CategoryDisplay) -> Unit = {},
    onAmountChange: (String) -> Unit = {},
    onSaveClick: () -> Unit ={},
    onSelectedUri: (Uri) -> Unit = {},
){
    var bgColor = ColorConstant.ExpensesRed

    state.type?.let {
        if (it == TransactionType.INCOME){
            bgColor = ColorConstant.IncomeGreen
        }
    }

    //DatePickerPromptDialog
    var datePickerDialogVisible by remember { mutableStateOf(false) }
    if (datePickerDialogVisible){
        DatePickerPromptDialog(
            onDateSelected = {
                it?.let(onDateSelected)
            },
            onDismiss = { datePickerDialogVisible = false }
        )
    }
    //TimePicker
    var timePickerDialogVisible by remember { mutableStateOf(false) }
    if (timePickerDialogVisible){
        TimePickerPromptDialog(
            onSelected = { hour, minute ->
                onTimeSelected.invoke(hour, minute)
            },
            onDismiss = { timePickerDialogVisible = false}
        )
    }

    //category select dialog()
    var categorySelectDialogVisible by remember { mutableStateOf(false) }
    if (categorySelectDialogVisible) {
        CategorySelectDialog(
            list = categories,
            onDismissRequest = { categorySelectDialogVisible = false },
            onCategorySelected = {
                onCategorySelected.invoke(it)
                categorySelectDialogVisible = false
            }
        )
    }

    Scaffold (
        containerColor = bgColor,
        topBar = {
            MmaTopBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = bgColor,
                    navigationIconContentColor = Color.White,
                    titleContentColor= Color.White,
                ),
                title = if (state.type == TransactionType.INCOME){
                    stringResource(id = R.string.feature_home_income)
                } else {
                    stringResource(id = R.string.feature_home_expenses)
                },
                onCloseClick = onTopBarLeftClick
            )
        },
        bottomBar = {
            Box(modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.dimens.dimen16)) {
                MmasButton(
                    modifier = Modifier.height(MaterialTheme.dimens.dimen56),
                    text = "Save",
                    onClick = onSaveClick
                )
            }

        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Column (modifier = Modifier
                .padding(horizontal = MaterialTheme.dimens.dimen32)){
                //how much
                Text(
                    text = "How much?",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))
                //amount
                Text(
                    text =  "$" + state.amountFormatted,
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White,
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))
            Column(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = MaterialTheme.dimens.dimen16,
                            topEnd = MaterialTheme.dimens.dimen16
                        )
                    )
                    .background(color = MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.dimen32)
            ){
                MmasTextEdit(
                    value = if (state.category != null) {
                        stringResource(id = state.category.stringResId)
                    } else {
                        ""
                    },
                    placeHolder = "Category",
                    readOnly = true,
                    leadingIcon = {
                        if (state.category != null){
                            Icon(
                                imageVector = ImageVector.vectorResource(state.category.imageResId),
                                contentDescription = stringResource(id = state.category.stringResId),
                                modifier = Modifier.size(
                                    MaterialTheme.dimens.dimen24
                                ),
                            )
                        }
                        else {
                            Icon(
                                imageVector = Icons.Default.Category,
                                contentDescription = "select catergory",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            categorySelectDialogVisible = true
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))
                MmasTextEdit(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    value = state.description,
                    placeHolder = "Description",
                    onValueChange = onDescriptionChange
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))

                Row (Modifier.fillMaxWidth()){
                    MmasTextEdit(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = MaterialTheme.dimens.dimen8),
                        value = state.dateString,
                        placeHolder = "Date",
                        readOnly = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        textEditOnClick = {
                            datePickerDialogVisible = true
                        }
                    )
                    MmasTextEdit(
                        value = state.timeString,
                        modifier = Modifier.weight(1f),
                        placeHolder = "Time",
                        readOnly = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.AvTimer,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        textEditOnClick = {
                            timePickerDialogVisible = true
                        }
                    )
                }
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))
                MmasTextEdit(
                    value = state.amountString,
                    placeHolder = "Amount",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.MonetizationOn,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    onValueChange = {
                        onAmountChange (
                            if (it.startsWith("0")) {
                                ""
                            } else {
                                it
                            }
                        )
                    },
                    visualTransformation = CurrencyAmountInputVisualTransformation(),
                    keyboardType = KeyboardType.NumberPassword
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))

                AddAttachmentRow(
                    uri = if (state.uri.isEmpty()){
                        Uri.EMPTY
                    } else {
                        state.uri.toUri()
                    },
                    onSelectedUri = onSelectedUri,
                    onDelete = {
                        onSelectedUri.invoke(Uri.EMPTY)
                    }
                )

            }
        }
    }
}

@Preview(apiLevel = 33, device = "spec:width=411dp,height=891dp", showBackground = true, showSystemUi = true)
@Composable
private fun InputScreenPreview(){
    MmasTheme {
        InputScreenContent()
    }
}