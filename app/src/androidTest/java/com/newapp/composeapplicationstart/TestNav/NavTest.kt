package com.newapp.composeapplicationstart.TestNav

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.newapp.composeapplicationstart.presentation.ui.compose.NavigationGraph
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setUpNavHost(){
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            NavigationGraph(navController = navController)
        }
    }

    @Test
    fun navigationGraph_verifyStartDestination(){
        composeTestRule
            .onAllNodesWithContentDescription("Start Screen")

    }

}