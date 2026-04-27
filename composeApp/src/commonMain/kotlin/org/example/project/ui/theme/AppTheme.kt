package org.example.project.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val LightColors = lightColorScheme(
    primary = Color(0xFF2F7BF6),
    onPrimary = Color.White,
    secondary = Color(0xFF50C8FF),
    background = Color(0xFFF4F6FB),
    surface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFFE8EEF8),
    onBackground = Color(0xFF161A23),
    onSurface = Color(0xFF161A23),
    onSurfaceVariant = Color(0xFF6A7385),
    primaryContainer = Color(0xFFDCEBFF),
    onPrimaryContainer = Color(0xFF0C2857)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF7CB4FF),
    onPrimary = Color(0xFF052F6B),
    secondary = Color(0xFF50C8FF),
    background = Color(0xFF0E1118),
    surface = Color(0xFF171C27),
    surfaceVariant = Color(0xFF253043),
    onBackground = Color(0xFFF1F4FA),
    onSurface = Color(0xFFF1F4FA),
    onSurfaceVariant = Color(0xFFABB7CC),
    primaryContainer = Color(0xFF1E3A66),
    onPrimaryContainer = Color(0xFFD8E7FF)
)

private val AwiTypography = Typography(
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        lineHeight = 36.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 30.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 26.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 18.sp
    )
)

@Composable
fun ProfileAppTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = AwiTypography,
        content = content
    )
}
