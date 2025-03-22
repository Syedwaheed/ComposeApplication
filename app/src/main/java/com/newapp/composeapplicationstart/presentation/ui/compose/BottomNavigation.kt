package com.newapp.composeapplicationstart.presentation.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.newapp.composeapplicationstart.presentation.utils.Screen
import com.newapp.composeapplicationstart.presentation.viewmodel.TrendingViewModel

@Composable
fun BottomNavigationBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val items = listOf(Screen.Home, Screen.Play, Screen.Profile)

    NavigationBar(containerColor = Color.Black, contentColor = Color.White) {
        items.forEach { screen ->
            val isSelected = currentRoute == screen.route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(screen.route) },
                icon = {
                    val iconRes = if(isSelected) screen.selectedIcon else screen.icon
                    DrawableImage(iconRes, screen.name)
                },
//                label = {
//                    Text(
//                        text = screen.name,
//                        color = if(isSelected) Color.White else Color.Gray
//                    )
//                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )

            )
        }
    }
}

@Composable
fun DrawableImage(id: Int, contentDescription: String) {
    Image(
        painter = painterResource(id = id),
        contentDescription = contentDescription,
        modifier = Modifier.size(24.dp),
    )
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = Screen.Home.route,
        modifier = modifier) {
        composable(Screen.Home.route) {
            val viewModel = hiltViewModel<TrendingViewModel>()
            val state by viewModel.trendingMovies.collectAsStateWithLifecycle()
            HomeScreen(state = state)
        }
        composable(Screen.Play.route) {
            PlayScreen(Screen.Play.name)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(Screen.Profile.name)
        }
    }
}

