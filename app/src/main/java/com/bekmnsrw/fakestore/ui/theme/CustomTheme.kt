package com.bekmnsrw.fakestore.ui.theme

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

@Composable
fun Theme(
    content: @Composable () -> Unit
) {

    val colors = baseLightPalette
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.bottomAppBarBackground.toArgb()
        }
    }

    CompositionLocalProvider(
        LocalColors provides colors,
        content = content
    )
}
