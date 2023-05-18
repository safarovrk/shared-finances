package com.example.core.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


data class CustomColors(
    val isLight: Boolean,
    val text: TextColors,
    val icon: IconColors,
    val background: BackgroundColors,
    val button: ButtonColors,
    val element: ElementColors
)

data class TextColors(
    val primary: Color,
    val secondary: Color,
    val button: Color,
    val disabled: Color,
    val alert: Color,
    val contrast: Color
)

data class IconColors(
    val primary: Color,
    val secondary: Color,
    val disabled: Color,
    val invert: Color,
    val contrast: Color
)

data class BackgroundColors(
    val primary: Color,
    val secondary: Color,
    val disabled: Color,
    val invert: Color,
    val photo: Color,
    val pressedPhoto: Color,
    val formError: Color
)

data class ButtonColors(
    val primary: Color,
    val secondary: Color,
    val disabled: Color
)

data class ElementColors(
    val primary: Color,
    val secondary: Color,
    val disabled: Color
)

fun CustomColors.toMaterialColors() = Colors(
    primary = button.primary,
    primaryVariant = button.primary,
    secondary = button.secondary,
    secondaryVariant = button.secondary,
    background = background.primary,
    surface = background.secondary,
    error = background.formError,
    onPrimary = text.button,
    onSecondary = text.button,
    onBackground = text.primary,
    onSurface = text.primary,
    onError = text.alert,
    isLight = false
)

val LocalCustomColors = staticCompositionLocalOf<CustomColors?> { null }