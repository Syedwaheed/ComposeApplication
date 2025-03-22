package com.newapp.composeapplicationstart.presentation.ui.theme

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),

    )

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = Color(0xFF1C1B1F),
    surface = Color(0xFF1C1B1F),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color(0xFFFFFBFE),
    onSurface = Color(0xFFFFFBFE),
    
    )

@Composable
fun ComposeApplicationStartTheme(
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
// Set the status bar color based on the theme
    SetStatusBarColor(darkTheme)
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// Function to set the status bar color and handle light/dark appearance
@Composable
fun SetStatusBarColor(darkTheme: Boolean) {
    val context = LocalContext.current
    val activity = context as? Activity
    activity?.window?.let { window ->
        // Set the status bar color based on the current theme
        val statusBarColor = if (darkTheme) {
            // Dark theme status bar color (dark gray or black)
            Color(0xFFF5F5F5).toArgb()  // Custom light status bar color

        } else {
            // Light theme status bar color (light gray or white)
            Color(0xFF121212).toArgb()  // Custom dark status bar color
        }

        // Set the status bar color for all devices
        window.statusBarColor = statusBarColor

        // For devices with API level 30 and above, modify system bar icons appearance
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11 and above
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                if (darkTheme) 0 else WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            // For devices below Android 11 (API 30), modify system UI visibility
            window.decorView.systemUiVisibility = if (darkTheme) {
                // Dark theme: light status bar icons (white icons on dark background)
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                // Light theme: dark status bar icons (black icons on light background)
                0 // No SYSTEM_UI_FLAG_LIGHT_STATUS_BAR flag, dark icons by default
            }
        }
    }
}
