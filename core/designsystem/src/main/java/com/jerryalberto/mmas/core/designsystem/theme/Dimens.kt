package com.jerryalberto.mmas.core.designsystem.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val dimen4:Dp = 4.dp,
    val dimen8:Dp = 8.dp,
    val dimen16:Dp = 16.dp,
    val dimen24:Dp = 24.dp,
    val dimen32:Dp = 32.dp,
    val dimen40:Dp = 40.dp,
    val dimen48:Dp = 48.dp,
    val dimen56:Dp = 56.dp,
    val dimen64:Dp = 64.dp,
    val dimen72:Dp = 72.dp,
    val dimen80:Dp = 80.dp,
    val dimen160:Dp = 160.dp,
    val iconSize:Dp = dimen32,
)

val compactDimens = Dimens(
)

val largeDimens = Dimens(
    dimen4 = 8.dp,
    dimen8 = 16.dp,
    dimen16 = 32.dp,
    iconSize = 64.dp
)

