package com.example.luckyconnect.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

// Футуристичная палитра цветов
object FuturisticColors {
    // Основные цвета
    val CyberBlue = Color(0xFF00D4FF)
    val NeonGreen = Color(0xFF39FF14)
    val ElectricPurple = Color(0xFF8A2BE2)
    val LaserRed = Color(0xFFFF073A)
    val QuantumGold = Color(0xFFFFD700)
    
    // Фоновые цвета
    val DeepSpace = Color(0xFF0A0A0F)
    val DarkMatter = Color(0xFF1A1A2E)
    val VoidBlack = Color(0xFF000000)
    val MidnightBlue = Color(0xFF16213E)
    
    // Поверхности
    val GlassMorphism = Color(0x1AFFFFFF)
    val CardSurface = Color(0xFF1E1E2E)
    val ElevatedSurface = Color(0xFF2A2A3A)
    
    // Текстовые цвета
    val TextPrimary = Color(0xFFFFFFFF)
    val TextSecondary = Color(0xFFB0B0B0)
    val TextTertiary = Color(0xFF707070)
    
    // Статусные цвета
    val SuccessGreen = Color(0xFF00FF7F)
    val WarningOrange = Color(0xFFFF8C00)
    val ErrorRed = Color(0xFFFF4757)
    val InfoBlue = Color(0xFF3742FA)
}

private val DarkColorScheme = darkColorScheme(
    primary = FuturisticColors.CyberBlue,
    onPrimary = FuturisticColors.VoidBlack,
    primaryContainer = FuturisticColors.MidnightBlue,
    onPrimaryContainer = FuturisticColors.CyberBlue,
    
    secondary = FuturisticColors.NeonGreen,
    onSecondary = FuturisticColors.VoidBlack,
    secondaryContainer = FuturisticColors.DarkMatter,
    onSecondaryContainer = FuturisticColors.NeonGreen,
    
    tertiary = FuturisticColors.ElectricPurple,
    onTertiary = FuturisticColors.TextPrimary,
    tertiaryContainer = FuturisticColors.CardSurface,
    onTertiaryContainer = FuturisticColors.ElectricPurple,
    
    background = FuturisticColors.DeepSpace,
    onBackground = FuturisticColors.TextPrimary,
    
    surface = FuturisticColors.CardSurface,
    onSurface = FuturisticColors.TextPrimary,
    surfaceVariant = FuturisticColors.ElevatedSurface,
    onSurfaceVariant = FuturisticColors.TextSecondary,
    
    error = FuturisticColors.ErrorRed,
    onError = FuturisticColors.TextPrimary,
    errorContainer = Color(0xFF2D1B1B),
    onErrorContainer = FuturisticColors.ErrorRed,
    
    outline = FuturisticColors.TextTertiary,
    outlineVariant = Color(0xFF3A3A3A),
    
    scrim = Color(0x80000000),
    inverseSurface = FuturisticColors.TextPrimary,
    inverseOnSurface = FuturisticColors.DeepSpace,
    inversePrimary = FuturisticColors.MidnightBlue,
    
    surfaceDim = FuturisticColors.DarkMatter,
    surfaceBright = FuturisticColors.ElevatedSurface,
    surfaceContainerLowest = FuturisticColors.VoidBlack,
    surfaceContainerLow = FuturisticColors.CardSurface,
    surfaceContainer = FuturisticColors.ElevatedSurface,
    surfaceContainerHigh = Color(0xFF363645),
    surfaceContainerHighest = Color(0xFF414155)
)

private val LightColorScheme = lightColorScheme(
    primary = FuturisticColors.MidnightBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD6E3FF),
    onPrimaryContainer = FuturisticColors.MidnightBlue,
    
    secondary = FuturisticColors.ElectricPurple,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE8DEF8),
    onSecondaryContainer = FuturisticColors.ElectricPurple,
    
    tertiary = FuturisticColors.CyberBlue,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFD0E4FF),
    onTertiaryContainer = FuturisticColors.MidnightBlue,
    
    background = Color(0xFFFEFBFF),
    onBackground = Color(0xFF1C1B1F),
    
    surface = Color(0xFFFEFBFF),
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
    
    error = Color(0xFFBA1A1A),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    
    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFFCAC4D0),
    
    scrim = Color.Black,
    inverseSurface = Color(0xFF313033),
    inverseOnSurface = Color(0xFFF4EFF4),
    inversePrimary = Color(0xFFAAC7FF)
)

@Composable
fun LuckyConnectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}