package com.balamitra.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Vibrant, High-Accessibility Indic Yellow & Deep Black Palette
val BrandYellowPrimary = Color(0xFFF0B31C) // Extracted from user reference
val BrandYellowDark = Color(0xFFD49A0E)
val BrandYellowLight = Color(0xFFFFF8E7)
val BrandYellowContainer = Color(0xFFFDF0CC)
val DeepBlack = Color(0xFF121212)
val CharcoalText = Color(0xFF1E272E)
val CharcoalLight = Color(0xFF485460)

// Backward compatibility aliases
val TerracottaPrimary = BrandYellowPrimary
val TerracottaDark = BrandYellowDark
val TerracottaLight = BrandYellowLight

val WarmSandBackground = Color(0xFFFBF9F4)
val CardBackground = Color(0xFFFFFFFF)
val TextPrimary = CharcoalText
val TextSecondary = CharcoalLight
val BorderSubtle = Color(0xFFE8E5DF)
val TagBackground = Color(0xFFF6F3EC)
val ForestGreen = Color(0xFF2E7D32)
val ForestGreenLight = Color(0xFFE8F5E9)
val TurmericWarm = Color(0xFFD97706)
val TurmericLight = Color(0xFFFEF3C7)
val SoftBlue = Color(0xFF1E6091)
val SoftBlueLight = Color(0xFFE0F2FE)

private val LightColorScheme = lightColorScheme(
    primary = BrandYellowPrimary,
    onPrimary = DeepBlack,
    primaryContainer = BrandYellowContainer,
    onPrimaryContainer = Color(0xFF422F00),
    secondary = DeepBlack,
    onSecondary = Color.White,
    secondaryContainer = BrandYellowLight,
    onSecondaryContainer = DeepBlack,
    tertiary = ForestGreen,
    onTertiary = Color.White,
    tertiaryContainer = ForestGreenLight,
    onTertiaryContainer = Color(0xFF14532D),
    background = WarmSandBackground,
    onBackground = TextPrimary,
    surface = CardBackground,
    onSurface = TextPrimary,
    surfaceVariant = TagBackground,
    onSurfaceVariant = TextSecondary,
    outline = BorderSubtle
)

// Rich Typography: Serif for human warm editorial headlines; SansSerif for crisp body & data
val BalamitraTypography = Typography(
    displayMedium = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        color = TextPrimary
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = TextPrimary
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp,
        color = TextPrimary
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
        color = TextPrimary
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.1.sp,
        color = TextPrimary
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.1.sp,
        color = TextSecondary
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.15.sp,
        color = TextPrimary
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 19.sp,
        letterSpacing = 0.1.sp,
        color = TextSecondary
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.1.sp,
        color = TextSecondary
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.2.sp,
        color = TextPrimary
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 15.sp,
        letterSpacing = 0.2.sp,
        color = TextSecondary
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.2.sp,
        color = TextSecondary
    )
)

@Composable
fun BalamitraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = BalamitraTypography,
        content = content
    )
}
