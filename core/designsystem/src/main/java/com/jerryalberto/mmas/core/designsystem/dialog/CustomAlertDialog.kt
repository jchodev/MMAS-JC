package com.jerryalberto.mmas.core.designsystem.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.designsystem.theme.dimens

@Composable
fun CustomAlertDialog(
    icon: @Composable (() -> Unit)? = null,

    title: String? = "this is title",
    message: String = "this is message",

    leftBtnStr: String = "Dismiss",
    onLeftClick: () -> Unit = {},

    rightBtnStr: String = "OK",
    onRightClick: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = { onLeftClick() },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        AlertDialogContent(
            icon = icon,
            title = title,
            message = message,
            leftBtnStr = leftBtnStr,
            onLeftClick = onLeftClick,
            rightBtnStr = rightBtnStr,
            onRightClick = onRightClick,
        )
    }
}


@Composable
private fun AlertDialogContent(
    modifier: Modifier = Modifier,

    icon: @Composable (() -> Unit)? = null,

    title: String? = "this is title",
    message: String = "this is message",

    leftBtnStr: String = "Dismiss",
    onLeftClick: () -> Unit = {},

    rightBtnStr: String = "OK",
    onRightClick: () -> Unit = {}
){
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(MaterialTheme.dimens.dimen8),

    ) {
        Column(
            modifier= modifier.background(MaterialTheme.colorScheme.background).fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            icon?.let {
                it.invoke()
            }

            Column(modifier = Modifier.padding(MaterialTheme.dimens.dimen16)) {
                title?.let {
                    Text(
                        text = it,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = MaterialTheme.dimens.dimen8)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Text(
                    text = message,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(
                            top = MaterialTheme.dimens.dimen8,
                            start = MaterialTheme.dimens.dimen24,
                            end = MaterialTheme.dimens.dimen24
                        )
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            //.......................................................................
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.dimens.dimen8)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                horizontalArrangement = Arrangement.SpaceAround) {

                TextButton(
                    onClick = onLeftClick
                ) {
                    Text(
                        text = leftBtnStr,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(top = MaterialTheme.dimens.dimen8, bottom = MaterialTheme.dimens.dimen8)
                    )
                }
                TextButton(onClick =onRightClick) {
                    Text(
                        text = rightBtnStr,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(top = MaterialTheme.dimens.dimen8, bottom = MaterialTheme.dimens.dimen8)
                    )
                }
            }
        }
    }
}



@Preview
@Composable
fun AlertDialogPreview(){
    MmasTheme {
        CustomAlertDialog(
            icon = {
                Icon(
                    modifier = Modifier.size(MaterialTheme.dimens.dimen160).padding(top = MaterialTheme.dimens.dimen32),
                    tint = MaterialTheme.colorScheme.error,
                    imageVector = Icons.Outlined.Error,
                    contentDescription = null
                )
            },
            title = null,
        )
    }
}
