package com.newapp.composeapplicationstart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.newapp.composeapplicationstart.presentation.ui.compose.BottomNavigationBar
import com.newapp.composeapplicationstart.presentation.ui.compose.NavigationGraph
import com.newapp.composeapplicationstart.presentation.ui.theme.ComposeApplicationStartTheme
import com.newapp.composeapplicationstart.presentation.utils.Screen
import com.newapp.composeapplicationstart.presentation.utils.setStatusBarColor
import dagger.hilt.android.AndroidEntryPoint
import android.app.Activity

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Enable drawing behind system bars
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        SetStatusBarColor()
        setContent {
            ComposeApplicationStartTheme {
//                MovieListScreen()
//                LaggingLazyColumn()
                MainScreen()
            }
        }
//        setStatusBarColor(android.graphics.Color.TRANSPARENT, isLightStatusBar = true)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    /*ModalNavigationDrawer(
      drawerState = drawerState,
      drawerContent = {
          Box(
              modifier = Modifier.fillMaxHeight()
                  .width(280.dp)
                  .background(Color.White)
                  .padding(
                      top = 50.dp,
                      start = 16.dp,
                      end = 16.dp,
                      bottom = 16.dp
                  )
          ) {
              Column{
                  Text(text = "Home", modifier = Modifier.clickable {
                      coroutineScope.launch {
                          drawerState.close()
                          navigateToScreen(navController,"home")
                      }
                  })
                  Spacer(modifier = Modifier.height(8.dp))
                  Text(text = "Play", modifier = Modifier.clickable {
                      coroutineScope.launch {
                          drawerState.close()
                          navigateToScreen(navController,"play")
                      }
                  })
                  Spacer(modifier = Modifier.height(8.dp))
                  Text(text = "Profile", Modifier.clickable {
                      coroutineScope.launch {
                          drawerState.close()
                          navigateToScreen(navController,"profile")
                      }
                  })
              }
          }

      }
  ) {}
  topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My App Bar") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Open Drawer",
                            tint = Color.White
                        )
                    }
                }
            )
        }*/

    Scaffold(
        bottomBar = {
            val currentRoute =
                navController.currentBackStackEntryAsState().value?.destination?.route
            BottomNavigationBar(currentRoute = currentRoute ?: Screen.Home.route) { route ->
                navigateToScreen(navController, route)
            }
        }
    ) { innerPadding ->
        NavigationGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }


}

private fun navigateToScreen(navController: NavHostController, route: String) {
    navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun LaggingLazyColumn() {
    val items = remember { List(1000) { "Item #$it" } } // Simulate a large dataset

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(items.size) { index ->
            // Simulate a heavy computation (e.g., sorting or expensive calculation)
            val heavyComputationResult = remember(index) { performHeavyComputation(index) }

            // Display each item
            Text(
                text = heavyComputationResult,
                modifier = Modifier.fillParentMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

fun performHeavyComputation(input: Int): String {
    // Simulate a time-consuming operation
    Thread.sleep(10) // Each item takes 10ms to process
    return "Processed Item: $input"
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeApplicationStartTheme {
//        LaggingLazyColumn()
    }
}
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ComposeApplicationStartTheme {
//        MySurfaceExample()
//    }
//}
//@Composable
//fun MySurfaceExample(){
//    Surface(
//        modifier = Modifier.padding(16.dp),
//        shape = RoundedCornerShape(20.dp),
//        color = MaterialTheme.colorScheme.secondary,
//        contentColor = contentColorFor(backgroundColor = MaterialTheme.colorScheme.primary),
//        tonalElevation = 10.dp,
//        shadowElevation = 10.dp,
//        onClick = {
//
//        }
//    ) {
//        Text(
//            text = "Hello World",
//            modifier = Modifier.padding(20.dp)
//        )
//    }
//}