package com.newapp.composeapplicationstart.presentation.utils

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsetsController

fun Activity.setStatusBarColor(color: Int, isLightStatusBar: Boolean) {
    window.statusBarColor = color // Set the status bar color

    // Adjust status bar text/icons color
    when {
        // For API 30 and above, use WindowInsetsController
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
            window.insetsController?.apply {
                setSystemBarsAppearance(
                    if (isLightStatusBar) WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS else 0,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            }
        }

        // For API 24 to 29, use View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        else -> {
            val decorView = window.decorView
            decorView.systemUiVisibility = if (isLightStatusBar) {
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            } else {
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
        }
    }
}
