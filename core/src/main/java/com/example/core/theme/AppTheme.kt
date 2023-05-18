package com.example.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val DarkAppColors = CustomColors(
    isLight = false,

    text = TextColors(
        primary = Color(0xFFFDFDFD),
        secondary = Color(0xFF646464),
        button = Color(0xFF3F3F3F),
        disabled = Color(0xFFA9A9A9),
        alert = Color(0xFFFF5F7C),
        contrast = Color(0xFFFFFFFF)
    ),
    icon = IconColors(
        primary = Color(0xFFFDFDFD),
        secondary = Color(0xFFE8E8E8),
        disabled = Color(0xFFA9A9A9),
        invert = Color(0xFF3F3F3F),
        contrast = Color(0xFFFFFFFF)
    ),
    background = BackgroundColors(
        primary = Color(0xFF3F3F3F),
        secondary = Color(0xFF909090),
        disabled = Color(0xFFDADADA),
        invert = Color(0xFFFDFDFD),
        photo = Color(0xFF323232),
        pressedPhoto = Color(0x4DFDFDFD),
        formError = Color(0xFFFFE6EE)
    ),
    button = ButtonColors(
        primary = Color(0xFFFDFDFD),
        secondary = Color(0xFF909090),
        disabled = Color(0xFFA9A9A9)
    ),
    element = ElementColors(
        primary = Color(0xFFFDFDFD),
        secondary = Color(0xFFE8E8E8),
        disabled = Color(0xFFA9A9A9)
    )
)

val LightAppColors = CustomColors(
    isLight = true,

    text = TextColors(
        primary = Color(0xFF3F3F3F),
        secondary = Color(0xFF828282),
        button = Color(0xFF404040),
        disabled = Color(0xFFD7D7D7),
        alert = Color(0xFFFF7D95),
        contrast = Color(0xFFFFFFFF)
    ),
    icon = IconColors(
        primary = Color(0xFF3F3F3F),
        secondary = Color(0xFFE8E8E8),
        disabled = Color(0xFFA9A9A9),
        invert = Color(0xFF404040),
        contrast = Color(0xFFFFFFFF)
    ),
    background = BackgroundColors(
        primary = Color(0xFFFFFFFF),
        secondary = Color(0xFFF6F6F6),
        disabled = Color(0xFFA9A9A9),
        invert = Color(0xFFF6F6F6),
        photo = Color(0x4D3F3F3F),
        pressedPhoto = Color(0x4DFDFDFD),
        formError = Color(0xFFFFF1F6)
    ),
    button = ButtonColors(
        primary = Color(0xFFF2F2F2),
        secondary = Color(0xFFA9A9A9),
        disabled = Color(0xFF909090)
    ),
    element = ElementColors(
        primary = Color(0xFF3F3F3F),
        secondary = Color(0xFFA9A9A9),
        disabled = Color(0xFFE8E8E8)
    )
)

val AppTypography = CustomTypography(
    title = TitleTypography(
        semiBoldLarge = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
        ),
        mediumLarge = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        )
    ),
    text = TextTypography(
        mediumNormal = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp
        )
    ),
    button = ButtonTypography(
        mediumNormal = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 17.sp
        )
    ),
    caption = CaptionTypography(
        mediumSystem = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 17.sp
        ),
        semiBoldSmall = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        ),
        regularSmall = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )
    )
)

@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (isDarkTheme) DarkAppColors else LightAppColors
    val typography = AppTypography

    CustomTheme(colors, typography) {
        MaterialTheme(
            colors = colors.toMaterialColors(),
            typography = typography.toMaterialTypography(),
            content = content
        )
    }
}

@Composable
fun CustomTheme(
    colors: CustomColors,
    typography: CustomTypography,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalCustomColors provides colors,
        LocalCustomTypography provides typography
    ) {
        content()
    }
}

object CustomTheme {

    val colors: CustomColors
        @Composable
        @ReadOnlyComposable
        get() = LocalCustomColors.current
            ?: throw Exception("CustomColors are not provided. Did you forget to apply CustomTheme?")

    val typography: CustomTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalCustomTypography.current
            ?: throw Exception("CustomTypography is not provided. Did you forget to apply CustomTheme?")
}
