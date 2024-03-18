package com.jerryalberto.mmas.feature.input.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.jerryalberto.mmas.core.designsystem.button.MmasButton
import com.jerryalberto.mmas.core.designsystem.edittext.MmasTextEdit
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.feature.input.ui.compones.SwitchTwoButton


@Composable
fun InputScreen() {
    InputScreenContent()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputScreenContent() {
    Column(
        modifier = Modifier
            .padding(MaterialTheme.dimens.dimen16)
            .fillMaxHeight()
    ) {
        SwitchTwoButton(
            button1Text = "Income",
            button2Text = "Expense",
            textStyle = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))

        var openCategorySelectDialog by remember { mutableStateOf(false) }

        MmasTextEdit(
            placeHolder = "Category",
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    openCategorySelectDialog = true
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
            placeHolder = "Description",
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))

        MmasTextEdit(
            placeHolder = "Amount",
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))

        Spacer(modifier = Modifier.weight(1f))


        MmasButton(

        )

    }
}