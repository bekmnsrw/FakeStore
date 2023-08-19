package com.bekmnsrw.fakestore.ui.theme

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

@Composable
fun Theme(
    content: @Composable () -> Unit
) {

    val colors = baseLightPalette
    val typography = typography()
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.bottomAppBarItemSelected.toArgb()
        }
    }

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides typography,
        content = content
    )
}

@Composable
fun typography(): CustomTypography = CustomTypography(
    cardTitle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    ),
    cardRating = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal
    ),
    cardDiscountPrice = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        textDecoration = TextDecoration.LineThrough
    ),
    cardFullPrice = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
)
