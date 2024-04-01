package com.jerryalberto.mmas.core.ui.component


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.core.ui.preview.DevicePreviews

@Composable
fun TransactionHeader(
    bgColor: Color = Color.Transparent,
    leftText: String = "this is left",
    rightText: String = "this is right",
    rightTextColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    rightTextOnClick: (() -> Unit)? = null,
) {
    Row (
        modifier = Modifier
            .background(color = bgColor)
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.dimen16),
        horizontalArrangement = Arrangement.Center
    ){
        Text(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            text = leftText,
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = if (rightTextOnClick != null){
                Modifier.clickable{
                    rightTextOnClick.invoke()
                }
            } else {
                Modifier
            },
            color = rightTextColor,
            text = rightText,
            style = MaterialTheme.typography.titleMedium
        )
    }

}


@DevicePreviews
@Composable
private fun TransactionHeaderPreview(){
    MmasTheme {
        TransactionHeader()
    }
}