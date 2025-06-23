package com.example.chatapplication.presentation.theme

import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import com.example.chatapplication.presentation.theme.enums.ScreenResolutionEnum
import com.example.chatapplication.presentation.theme.spacing.Spacing
import com.example.chatapplication.presentation.theme.spacing.SpacingDefault
import com.example.chatapplication.presentation.theme.spacing.SpacingXXHDPI

typealias Theme = MaterialTheme

val Theme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current

val LocalSpacing = compositionLocalOf<Spacing> {
    error("no spacing provided")
}

object LocalScreen {
    val current: Spacing
        @Composable
        get() = when (screenResolution(LocalContext.current)) {
            ScreenResolutionEnum.XHdpi -> SpacingDefault()
            ScreenResolutionEnum.XXHdpi -> SpacingDefault()
            ScreenResolutionEnum.XXXHdpi -> SpacingXXHDPI()
        }
}

private fun screenResolution(context: Context): ScreenResolutionEnum {
    return when (context.resources.displayMetrics.densityDpi) {
        in 0..240 -> ScreenResolutionEnum.XHdpi
        in 241..400 -> ScreenResolutionEnum.XXHdpi
        else -> ScreenResolutionEnum.XXXHdpi
    }
}
