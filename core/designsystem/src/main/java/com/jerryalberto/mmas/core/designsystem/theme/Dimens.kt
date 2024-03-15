package com.jerryalberto.mmas.core.designsystem.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val spacing4:Dp = 4.dp,
    val spacing8:Dp = 8.dp,
    val spacing16:Dp = 16.dp,
    val spacing32:Dp = 32.dp,
    val iconSize:Dp = 48.dp
)

val compactDimens = Dimens(
)

val largeDimens = Dimens(
    spacing4 = 8.dp,
    spacing8 = 16.dp,
    spacing16 = 32.dp,
    iconSize = 64.dp
)

