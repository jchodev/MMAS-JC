package com.jerryalberto.mmas.feature.home.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.AvTimer
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold

import androidx.compose.material3.Text


import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.tooling.preview.Preview
import com.jerryalberto.mmas.core.designsystem.button.MmasButton
import com.jerryalberto.mmas.core.designsystem.dialog.DatePickerPromptDialog
import com.jerryalberto.mmas.core.designsystem.dialog.TimePickerPromptDialog
import com.jerryalberto.mmas.core.designsystem.constant.ColorConstant
import com.jerryalberto.mmas.core.designsystem.dialog.CategorySelectDialog
import com.jerryalberto.mmas.core.designsystem.edittext.MmasTextEdit
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.core.designsystem.topbar.MmaTopBar
import com.jerryalberto.mmas.core.model.data.Category
import com.jerryalberto.mmas.feature.home.ui.component.AddAttachmentRow
import com.jerryalberto.mmas.feature.home.ui.viewmodel.InputScreenViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InputScreen(
    viewModel: InputScreenViewModel,
    onTopBarLeftClick: () -> Unit = {},
) {
    InputScreenContent(
        onTopBarLeftClick = onTopBarLeftClick,
        categories = viewModel.getCategories()
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputScreenContent(
    onTopBarLeftClick: () -> Unit = {},
    categories: List<Category> = listOf()
){
    val bgColor = ColorConstant.ExpensesRed

    //DatePickerPromptDialog
    var datePickerDialogVisible by remember { mutableStateOf(false) }
    if (datePickerDialogVisible){
        DatePickerPromptDialog(
            dateFormat = "dd/MM/yyyy",
            onDateSelected = {
                //    date = it
            },
            onDismiss = { datePickerDialogVisible = false }
        )
    }
    //TimePicker
    var timePickerDialogVisible by remember { mutableStateOf(false) }
    if (timePickerDialogVisible){
        TimePickerPromptDialog(
            onSelected = {
                    hour, minute ->
                // Simulate action on selection (optional)
                //Log.d("TimePicker", "Selected time: $hour:$minute")
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
//            onSelected = {
//                openUserCountrySelectDialog = false
//                Timber.d("selected individualBusinessCountry is ${it.cCountryCode}")
//                viewModel.onUserCountryChange(it)
//            }
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
                title = "App",
                onCloseClick = onTopBarLeftClick
            )
        },
        bottomBar = {
            Box(modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.dimens.dimen16)) {
                MmasButton(
                    modifier = Modifier.height(MaterialTheme.dimens.dimen56),
                    text = "Save"
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
                    text = "$ 1000",
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
                    placeHolder = "Category",
                    readOnly = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Category,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
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
                    placeHolder = "Description",
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))


                Row (Modifier.fillMaxWidth()){
                    MmasTextEdit(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = MaterialTheme.dimens.dimen8),
                        placeHolder = "Date",
                        readOnly = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                datePickerDialogVisible = true
                            }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Select Date",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    )
                    MmasTextEdit(
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
                        trailingIcon = {
                            IconButton(onClick = {
                                timePickerDialogVisible = true
                            }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Select Time",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))
                MmasTextEdit(
                    placeHolder = "Amount",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.MonetizationOn,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))

                AddAttachmentRow()

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