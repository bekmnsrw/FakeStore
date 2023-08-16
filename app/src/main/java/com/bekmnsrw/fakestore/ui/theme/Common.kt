package com.bekmnsrw.fakestore.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class Colors(
    val bottomAppBarBackground: Color,
    val bottomAppBarItemSelected: Color,
    val bottomAppBarItemUnselected: Color
)

object CustomTheme {
    val colors: Colors
        @Composable get() = LocalColors.current
}

val LocalColors = staticCompositionLocalOf<Colors> { error("No colors provided") }
val LocalTypography = staticCompositionLocalOf<Typography> { error("No typography provided") }
