package com.bekmnsrw.fakestore.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

data class Colors(
    val bottomAppBarBackground: Color,
    val bottomAppBarItemSelected: Color,
    val bottomAppBarItemUnselected: Color,

    val rate: Color,

    val isFavoriteSelected: Color,
    val isFavoriteUnselected: Color,

    val background: Color,
    val onBackground: Color,

    val cardMainText: Color,
    val cardSupportingText: Color,
    val cardBackground: Color,

    val progressBar: Color,

    val inStockIcon: Color,
    val boughtIcon: Color
)

data class CustomTypography(
    val cardTitle: TextStyle,
    val cardRating: TextStyle,
    val cardFullPrice: TextStyle,
    val cardDiscountPrice: TextStyle,

    val detailsTitle: TextStyle,
    val detailsDescription: TextStyle,

    val detailsCardTitle: TextStyle,
    val detailsCardSupportingText: TextStyle,

    val detailsFullPrice: TextStyle,
    val detailsDiscountPrice: TextStyle,

    val detailsListItem: TextStyle,

    val sellerRating: TextStyle,
    val sellerMarks: TextStyle,

    val rationaleDialogTitle: TextStyle,
    val rationaleDialogText: TextStyle,
    val rationaleDialogConfirmButton: TextStyle,
    val rationaleDialogDismissButton: TextStyle,

    val categoryCard: TextStyle,

    val categoryCardMainScreen: TextStyle
)

object CustomTheme {
    val colors: Colors
        @Composable get() = LocalColors.current

    val typography: CustomTypography
        @Composable get() = LocalTypography.current
}

val LocalColors = staticCompositionLocalOf<Colors> { error("No colors provided") }
val LocalTypography = staticCompositionLocalOf<CustomTypography> { error("No typography provided") }
