package com.example.core.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

data class CustomTypography(
    val title: TitleTypography,
    val text: TextTypography,
    val button: ButtonTypography,
    val caption: CaptionTypography
)

data class TitleTypography(
    val semiBoldLarge: TextStyle,
    val mediumLarge: TextStyle,

)

data class TextTypography(
    val mediumNormal: TextStyle
)

data class ButtonTypography(
    val mediumNormal: TextStyle
)

data class CaptionTypography(
    val mediumSystem: TextStyle,
    val semiBoldSmall: TextStyle,
    val regularSmall: TextStyle
)

fun CustomTypography.toMaterialTypography() = Typography(
    h1 = title.semiBoldLarge,
    h2 = title.mediumLarge,
    subtitle1 = text.mediumNormal,
    button = button.mediumNormal,
    caption = caption.mediumSystem,
    overline = caption.regularSmall
)

val LocalCustomTypography = staticCompositionLocalOf<CustomTypography?> { null }
