package com.newapp.composeapplicationstart.presentation.ui.compose

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.newapp.composeapplicationstart.domain.model.response.TrendingMovieResponse
import com.newapp.composeapplicationstart.presentation.utils.Details
import com.newapp.composeapplicationstart.presentation.utils.Screen
import com.newapp.composeapplicationstart.presentation.utils.Settings
import com.newapp.composeapplicationstart.presentation.viewmodel.TrendingViewModel
import kotlin.reflect.typeOf

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
                    val iconRes = if (isSelected) screen.selectedIcon else screen.icon
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
    NavHost(
        navController = navController, startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            val viewModel = hiltViewModel<TrendingViewModel>()
            val state by viewModel.trendingMovies.collectAsStateWithLifecycle()
            HomeScreen(state = state, onItemClick = { movie ->
                navController.navigate(
                    route = Details(
                        movie
                    )
                )
            }, onDialog = {
                navController.navigate(route = Settings)
            }
            )
        }
        dialog<Settings> {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
        composable<Details>(
            typeMap = mapOf(
                typeOf<TrendingMovieResponse?>() to GenericNavTypeFactory.createNavType<TrendingMovieResponse?>(
                    isNullableAllowed = true
                )
            )
        ) { backStackEntry ->
            val movie = backStackEntry.toRoute<Details>()
            DetailsScreen(movie = movie.movieResponse)
        }
        composable(Screen.Play.route) {
            PlayScreen(Screen.Play.name)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(Screen.Profile.name)
        }
    }
}

@Composable
fun SettingsScreen(modifier: Modifier = Modifier,
                   onBack: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Color.Black.copy(alpha = 0.5f)
            )
            .clickable {
                onBack()
            }
    ){
        Surface(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(32.dp)
                .fillMaxWidth()
                .wrapContentSize(),
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 8.dp,
            shadowElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Settings Screen",
                    style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "This is a settings screen.",
                    style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "You can add your settings options here.",
                    style = MaterialTheme.typography.bodyMedium)
                Button(onClick = onBack){
                 Text(text= "Close")
                }
            }
        }
    }
}
@Composable
fun DetailsScreen(movie: TrendingMovieResponse?) {
    Box(Modifier.testTag("DetailsScreen")){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Log.d("NavTest", "Navigated to Details Screen with ID: ${movie?.id}")
            Toast.makeText(LocalContext.current, "Navigated to Details", Toast.LENGTH_SHORT).show()
            Text(text = "Details Screen")

        }
    }
}

@Preview
@Composable
fun DetailsScreenPreview() {
    DetailsScreen(
        movie =
            TrendingMovieResponse(
                id = 1,
                name = "Movie Name",
                posterPath = "poster_path",
                rating = 4.5
            )
    )
}

