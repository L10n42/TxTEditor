package com.kappdev.txteditor.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFA9E97C),
    onPrimary = Color(0xFF213400),
    primaryContainer = Color(0xFF354B00),
    onPrimaryContainer = Color(0xFFC7F59C),
    inversePrimary = Color(0xFF76D300),
    secondary = Color(0xFFB9CBA5),
    onSecondary = Color(0xFF283300),
    secondaryContainer = Color(0xFF404C32),
    onSecondaryContainer = Color(0xFFE2F4C8),
    background = Color(0xFF121212),
    onBackground = Color(0xFFE6E6E6),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFE6E6E6),
    surfaceVariant = Color(0xFF414942),
    onSurfaceVariant = Color(0xFFC2C9BF),
    inverseSurface = Color(0xFFE6E6E6),
    inverseOnSurface = Color(0xFF121212),
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410),
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFF9DEDC),
    outline = Color(0xFF8F9389),
    outlineVariant = Color(0xFF3A3F37),
    scrim = Color(0xFF000000),
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF76D300),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFE4F9C6),
    onPrimaryContainer = Color(0xFF203600),
    inversePrimary = Color(0xFFA9E97C),
    secondary = Color(0xFF606C38),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD6E8C7),
    onSecondaryContainer = Color(0xFF192200),
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF1E1E1E),
    surface = Color(0xFFF8F8F8),
    onSurface = Color(0xFF1E1E1E),
    surfaceVariant = Color(0xFFE1E4DB),
    onSurfaceVariant = Color(0xFF414942),
    inverseSurface = Color(0xFF2E2E2E),
    inverseOnSurface = Color(0xFFF0F0F0),
    error = Color(0xFFB3261E),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B),
    outline = Color(0xFF74796D),
    outlineVariant = Color(0xFFCBD0C5),
    scrim = Color(0xFF000000),
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
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

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, window.decorView).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}