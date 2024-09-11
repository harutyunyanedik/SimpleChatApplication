package com.example.mangochatapplication.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    background = color6,
    onBackground = color1,
    primary = color2,
    onPrimary = color1,
    secondary = color7,
    onSecondary = color1,
    tertiary = color3,
    error = color9,
    onError = color1
)

@Composable
fun MangoChatApplicationTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        else -> DarkColorScheme
    }
    val systemUiController = rememberSystemUiController()
    val view = LocalView.current
    if (!view.isInEditMode) {

        SideEffect {
            systemUiController.setStatusBarColor(colorScheme.background)
            systemUiController.setNavigationBarColor(colorScheme.background)
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}