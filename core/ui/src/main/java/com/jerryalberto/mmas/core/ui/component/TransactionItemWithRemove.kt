package com.jerryalberto.mmas.core.ui.component


import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberSwipeToDismissBoxState


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jerryalberto.mmas.core.designsystem.other.DragAnchors
import com.jerryalberto.mmas.core.designsystem.other.DraggableItem
import com.jerryalberto.mmas.core.designsystem.theme.dimens


import com.jerryalberto.mmas.core.model.data.Setting
import com.jerryalberto.mmas.core.model.data.Transaction
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("RememberReturnType")
@Composable
fun TransactionItemWithRemove(
    modifier: Modifier = Modifier,
    initialValue: DragAnchors = DragAnchors.Center,
    setting: Setting = Setting(),
    showTimeOnly: Boolean = true,
    transaction: Transaction,
    onDelete: (Transaction) -> Unit = {}
) {
    val density = LocalDensity.current

    val defaultActionSize = MaterialTheme.dimens.dimen80
    val actionSizePx = with(density) { defaultActionSize.toPx() }
    val endActionSizePx = with(density) { (defaultActionSize).toPx() }

    val state = remember {
        AnchoredDraggableState(
            initialValue = initialValue,
            anchors = DraggableAnchors {
                DragAnchors.Start at -actionSizePx
                DragAnchors.Center at 0f
                DragAnchors.End at endActionSizePx
            },
            positionalThreshold = { distance: Float -> distance * 0.8f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            animationSpec = tween(),
        ).apply {
            updateAnchors(
                DraggableAnchors {
                    DragAnchors.Start at 0f
                    DragAnchors.End at endActionSizePx
                }
            )
        }
    }
    val scope = rememberCoroutineScope()
    DraggableItem(
        height =  MaterialTheme.dimens.dimen80,
        state = state,
        startAction = {},
        endAction = {
            Box(
                modifier = Modifier
                    .background(color = Color.Red)
                    .width(defaultActionSize)
                    .fillMaxHeight()
                    .align(Alignment.CenterEnd),
            ) {
                Box(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.error)
                        .fillMaxSize()
                        .clickable {
                            scope.launch {
                                state.animateTo(DragAnchors.Start)
                            }
                            onDelete.invoke(transaction)
                        }
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier.size(MaterialTheme.dimens.dimen24),
                            imageVector = Icons.Filled.Delete,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onError
                        )
                        Text(
                            text = "Delete",
                            color = MaterialTheme.colorScheme.onError,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }
            }
        },

        content = {
            TransactionItem(
                setting = setting,
                showTimeOnly = showTimeOnly,
                modifier = modifier,
                transaction = transaction,
            )
        }
    )
}
