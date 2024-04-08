package com.jerryalberto.mmas.feature.transaction.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.core.model.data.Setting
import com.jerryalberto.mmas.core.ui.ext.convertMillisToDate
import com.jerryalberto.mmas.core.model.data.Transaction
import com.jerryalberto.mmas.core.model.data.TransactionType
import com.jerryalberto.mmas.core.ui.component.TransactionHeader
import com.jerryalberto.mmas.core.ui.component.TransactionItem
import com.jerryalberto.mmas.core.ui.constants.ColorConstant
import com.jerryalberto.mmas.core.ui.ext.formatAmount
import com.jerryalberto.mmas.core.ui.helper.UiHelper
import com.jerryalberto.mmas.core.ui.preview.DevicePreviews
import com.jerryalberto.mmas.feature.transaction.model.TransactionData
import java.util.Calendar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionsList(
    setting: Setting = Setting(),
    modifier:Modifier = Modifier,
    transactionData: List<TransactionData> = listOf()
) {
    Box(
      modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier
            //.background(Color.White)
            .fillMaxSize()) {
            transactionData.forEachIndexed { index, group->
                stickyHeader {
                    Column(modifier = Modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.background)) {
                        if (index > 0) {
                            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen8))
                        }
                        TransactionHeader(
                            bgColor = MaterialTheme.colorScheme.surfaceVariant,
                            leftText = group.date.convertMillisToDate(setting.dateFormat),
                            rightText = group.totalAmount.formatAmount(setting = setting, withPlus = true),
                            rightTextColor =  if (group.totalAmount < 0.0) ColorConstant.ExpensesRed else ColorConstant.IncomeGreen,
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen8))
                    }

                }

                items(
                    items = group.transactions
                ) {
                    TransactionItem(
                        setting = setting,
                        transaction = it
                    )
                }
            }

        }
    }
}

@DevicePreviews
@Composable
private fun LazyColumnWithStickyHeaderPreview(){

    MmasTheme {
        TransactionsList(
            transactionData = listOf(
                TransactionData(
                    date = Calendar.getInstance().timeInMillis,
                    totalAmount = 0.0,
                    transactions = listOf(
                        Transaction(
                            id = 1,
                            type = TransactionType.EXPENSES,
                            amount = 1.0,
                            description = "description",
                            date =  Calendar.getInstance().timeInMillis,
                            hour = 1,
                            minute = 1,
                            uri = ""
                        ),
                        Transaction(
                            id = 2,
                            type = TransactionType.EXPENSES,
                            amount = 2.0,
                            description = "description2",
                            date =  Calendar.getInstance().timeInMillis,
                            hour = 2,
                            minute = 1,
                            uri = ""
                        )
                    )
                )
            )
        )
    }

}
