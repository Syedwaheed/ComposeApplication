package com.newapp.composeapplicationstart.presentation.utils

import com.newapp.composeapplicationstart.R

sealed class Screen(val route: String, val name: String, val icon: Int, val selectedIcon: Int) {
    data object Home :
        Screen("home", "Home Screen", R.drawable.home_unselected, R.drawable.home_selected)

    data object Play : Screen("play", "Play Screen", R.drawable.play, R.drawable.play_selected)
    data object Profile :
        Screen("profile", "Profile Screen", R.drawable.profile, R.drawable.profile_selected)
}