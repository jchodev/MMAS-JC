package com.jerryalberto.mmas.feature.home.ui.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.designsystem.theme.dimens
import com.jerryalberto.mmas.feature.home.R

@Composable
fun AddAttachmentRow(
    uri: Uri = Uri.EMPTY,
    onSelectedUri: (Uri) -> Unit = {},
    onDelete: () -> Unit = {}
) {

    val selfieImagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            it?.let {
                onSelectedUri.invoke(it)
            }
        }
    )
    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    selfieImagePicker.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = Icons.Default.AttachFile,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimens.dimen8))
            Text(
                text = "Add attachment",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.titleMedium
            )
        }
        if (uri != Uri.EMPTY) {
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Box(
                    modifier = Modifier.size(MaterialTheme.dimens.dimen160),
                ) {
                    AsyncImage(
                        model = uri,
                        modifier = Modifier
                            .aspectRatio(1.0f)
                            .align(Alignment.Center),
                        contentDescription = null,
                    )
                    // Overlay the "X" button on top right
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Surface(
                            modifier = Modifier.size(
                                MaterialTheme.dimens.iconSize
                            ),
                            shape = CircleShape,
                            color = Color.Gray
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Delete image",
                                tint = Color.White
                            )
                        }
                    }
                }
            }


        }
    }
}

@Preview(apiLevel = 33, device = "spec:width=411dp,height=891dp", showBackground = true, showSystemUi = true)
@Composable
private fun AddAttachmentRowPreview(
) {
    MmasTheme {
        AddAttachmentRow(
            uri = "android.resource://${LocalContext.current.packageName}/${R.drawable.ic_parking}".toUri()
        )
    }
}