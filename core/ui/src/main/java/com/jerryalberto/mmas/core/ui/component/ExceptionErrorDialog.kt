package com.jerryalberto.mmas.core.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.jerryalberto.mmas.core.designsystem.dialog.ErrorDialog
import com.jerryalberto.mmas.core.ui.R

@Composable
fun ExceptionErrorDialog(
    exception: Throwable,
    onRetryRequest: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    ErrorDialog(
        text = getMessageFromException(exception = exception),
        onRetryRequest = onRetryRequest,
        onDismissRequest = onDismissRequest,
    )
}

@Composable
private fun getMessageFromException(exception: Throwable): String{
    val errorMessage = exception.message ?: ""
    if (errorMessage.isEmpty()){
        return stringResource(id = R.string.core_ui_common_error)
    }
    else {
        return errorMessage
    }
}