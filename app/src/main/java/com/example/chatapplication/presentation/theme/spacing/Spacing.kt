package com.example.chatapplication.presentation.theme.spacing

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class SpacingDefault(
    override val default: Dp = 8.dp,
    override val tiny: Dp = 2.dp,
    override val extraSmall: Dp = 4.dp,
    override val small: Dp = 8.dp,
    override val medium: Dp = 16.dp,
    override val large: Dp = 32.dp,
) : Spacing

data class SpacingXXHDPI(
    override val default: Dp = 8.dp,
    override val tiny: Dp = 2.dp,
    override val extraSmall: Dp = 4.dp,
    override val small: Dp = 8.dp,
    override val medium: Dp = 16.dp,
    override val large: Dp = 32.dp,
) : Spacing

interface Spacing {
    val default: Dp
    val tiny: Dp
    val extraSmall: Dp
    val small: Dp
    val medium: Dp
    val large: Dp
}