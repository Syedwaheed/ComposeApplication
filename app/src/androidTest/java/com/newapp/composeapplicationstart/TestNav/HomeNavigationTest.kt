package com.newapp.composeapplicationstart.TestNav

import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.newapp.composeapplicationstart.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeNavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun clickMovieItem_navigatesToDetailScreen() {
        val movieID = 229711
        val movieTestTag = "MovieItem$movieID"
        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithTag(movieTestTag,useUnmergedTree = true).fetchSemanticsNodes().isNotEmpty()
        }


        //Perform Click
        composeTestRule.onNodeWithTag(movieTestTag, useUnmergedTree = true)
            .assertExists("MovieItem with test tag $movieTestTag not found.")
            .performClick()

        composeTestRule
            .onNodeWithTag("DetailsScreen")
            .assertExists("Details Screen not found.")
    }
}