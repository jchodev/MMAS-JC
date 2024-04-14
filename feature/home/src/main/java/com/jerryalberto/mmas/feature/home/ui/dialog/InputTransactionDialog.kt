package com.jerryalberto.mmas.feature.home.ui.dialog

import android.net.Uri
import android.widget.Toast
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
import androidx.compose.material3.BasicAlertDialog
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jerryalberto.mmas.core.designsystem.button.MmasButton
import com.jerryalberto.mmas.core.designsystem.dialog.DatePickerPromptDialog
import com.jerryalberto.mmas.core.designsystem.dialog.TimePickerPromptDialog
import com.jerryalberto.mmas.core.designsystem.edittext.MmasTextEdit
import com.jerryalberto.mmas.core.designsystem.text.AutoResizedText
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.core.designsystem.topbar.MmaTopBar
import com.jerryalberto.mmas.core.designsystem.utils.CurrencyAmountInputVisualTransformation
import com.jerryalberto.mmas.core.model.data.Category
import com.jerryalberto.mmas.core.model.data.Setting
import com.jerryalberto.mmas.core.model.data.TransactionType
import com.jerryalberto.mmas.core.ui.component.LoadingCompose
import com.jerryalberto.mmas.core.ui.ext.convertMillisToDate
import com.jerryalberto.mmas.core.ui.ext.displayHourMinute
import com.jerryalberto.mmas.core.ui.ext.formatAmount
import com.jerryalberto.mmas.core.ui.ext.getColors
import com.jerryalberto.mmas.core.ui.ext.getImageVector
import com.jerryalberto.mmas.core.ui.ext.getString
import com.jerryalberto.mmas.core.ui.preview.DevicePreviews
import com.jerryalberto.mmas.feature.home.R
import com.jerryalberto.mmas.feature.home.ui.component.CategorySelectDialog
import com.jerryalberto.mmas.feature.home.ui.data.InputTransactionData
import com.jerryalberto.mmas.feature.home.ui.viewmodel.InputDialogViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTransactionDialog(
    modifier: Modifier = Modifier,
    viewModel: InputDialogViewModel = hiltViewModel(),
    setting: Setting,
    onDismissRequest: () -> Unit = {},
    transactionType: TransactionType = TransactionType.INCOME,
    properties: DialogProperties = DialogProperties().let {
        DialogProperties(
            dismissOnBackPress = it.dismissOnBackPress,
            dismissOnClickOutside = it.dismissOnClickOutside,
            securePolicy = it.securePolicy,
            usePlatformDefaultWidth = false,
        )
    },
) {


    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    BasicAlertDialog(
        modifier = modifier.fillMaxSize(),
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            InputTransactionContent(
                state = uiState.data,
                setting = setting,
                onTopBarLeftClick = onDismissRequest,
                expensesCategories = viewModel.getExpenseCategories(),
                incomeCategories = viewModel.getIncomeCategories(),
                onDescriptionChange = viewModel::onDescriptionChange,
                onDateSelected = viewModel::onDateSelected,
                onTimeSelected = viewModel::onTimeSelected,
                onCategorySelected = viewModel::onCategorySelected,
                onAmountChange = viewModel::onAmountChange,
                onSaveClick = viewModel::saveTransaction,
                onSelectedUri = viewModel::onSelectedUri,
            )
            if (uiState.data.isSuccess){
                Toast.makeText(LocalContext.current, stringResource(id = R.string.feature_home_transaction_saved), Toast.LENGTH_SHORT).show()
                onDismissRequest.invoke()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputTransactionContent(
    state: InputTransactionData = InputTransactionData(),
    setting: Setting = Setting(),
    onTopBarLeftClick: () -> Unit = {},
    incomeCategories: List<Category> = listOf(),
    expensesCategories: List<Category> = listOf(),
    onDescriptionChange: (String)-> Unit = {},
    onDateSelected: (Long) -> Unit = {},
    onTimeSelected: (Int, Int) -> Unit = {
            hour, minute ->
    },
    onCategorySelected: (Category) -> Unit = {},
    onAmountChange: (String) -> Unit = {},
    onSaveClick: () -> Unit ={},
    onSelectedUri: (Uri) -> Unit = {},
){
    val transactionType = state.transaction.type ?: TransactionType.EXPENSES

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
            defaultHour = state.transaction.hour ?: Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            defaultMin = state.transaction.minute ?: Calendar.getInstance().get(Calendar.MINUTE),
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
            transactionType = transactionType,
            list = if (transactionType == TransactionType.EXPENSES) expensesCategories else incomeCategories,
            onDismissRequest = { categorySelectDialogVisible = false },
            onCategorySelected = {
                onCategorySelected.invoke(it)
                categorySelectDialogVisible = false
            }
        )
    }

    Scaffold (
        containerColor = transactionType.getColors().first,
        topBar = {
            MmaTopBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = transactionType.getColors().first,
                    navigationIconContentColor = Color.White,
                    titleContentColor= Color.White,
                ),
                title = if (transactionType == TransactionType.INCOME){
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
                    text = stringResource(id = R.string.feature_home_save),
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
                    text = stringResource(id = R.string.feature_home_how_much),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))
                //amount
                AutoResizedText(
                    text =  state.transaction.amount.formatAmount(setting = setting, withPlus = false),
                    //text = state.amountString,
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
                    value = if (state.transaction.category != null) {
                        state.transaction.category!!.type.getString()
                    } else {
                        ""
                    },
                    error = state.categoryError?.let { stringResource(id = it) },
                    placeHolder = stringResource(id = R.string.feature_home_category),
                    readOnly = true,
                    leadingIcon = {
                        if (state.transaction.category != null) {
                            Icon(
                                imageVector = state.transaction.category!!.type.getImageVector(),
                                contentDescription = state.transaction.category!!.type.getString(),
                                modifier = Modifier.size(
                                    MaterialTheme.dimens.dimen24
                                ),
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Category,
                                contentDescription = stringResource(id = R.string.feature_home_select_category),
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
                            contentDescription = stringResource(id = R.string.feature_home_description),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    value = state.transaction.description,
                    error = state.descriptionError?.let { stringResource(id = it) },
                    placeHolder = stringResource(id = R.string.feature_home_description),
                    onValueChange = onDescriptionChange
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))

                Row (Modifier.fillMaxWidth()){
                    MmasTextEdit(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = MaterialTheme.dimens.dimen8),
                        value = state.transaction.date?.convertMillisToDate(setting.dateFormat) ?: "",
                        error = state.dateError?.let { stringResource(id = it) },
                        placeHolder = stringResource(id = R.string.feature_home_date),
                        readOnly = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = stringResource(id = R.string.feature_home_date),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        textEditOnClick = {
                            datePickerDialogVisible = true
                        }
                    )
                    MmasTextEdit(
                        value = state.transaction.displayHourMinute(setting = setting),
                        error = state.timeError?.let { stringResource(id = it) } ,
                        modifier = Modifier.weight(1f),
                        placeHolder = stringResource(id = R.string.feature_home_time),
                        readOnly = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.AvTimer,
                                contentDescription = stringResource(id = R.string.feature_home_time),
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
                    error = state.amountError?.let { stringResource(id = it) },
                    placeHolder = stringResource(id = R.string.feature_home_amount),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.MonetizationOn,
                            contentDescription = stringResource(id = R.string.feature_home_amount),
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

                //Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))

//                AddAttachmentRow(
//                    uri = if (state.transaction.uri.isEmpty()){
//                        Uri.EMPTY
//                    } else {
//                        state.transaction.uri.toUri()
//                    },
//                    onSelectedUri = onSelectedUri,
//                    onDelete = {
//                        onSelectedUri.invoke(Uri.EMPTY)
//                    }
//                )

            }
        }
    }
}

@DevicePreviews
@Composable
private fun InputTransactionDialogContentPreview(){
    MmasTheme {
        InputTransactionContent()
    }
}