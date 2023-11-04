package org.chrivin.hsrcomposeapp

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import org.chrivin.hsrcomposeapp.model.HSRCharacterData
import org.chrivin.hsrcomposeapp.ui.navigation.Screen
import org.chrivin.hsrcomposeapp.ui.theme.HSRComposeAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HSRComposeAppKtTest{
    @get : Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            HSRComposeAppTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                HSRComposeApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartIsHome() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_navigatesFromHome_ToDetailWithData() {
        composeTestRule.onNodeWithTag("lazy_list_column").performScrollToIndex(2)
        composeTestRule.onNodeWithText(HSRCharacterData.dummyHSRCharacter[2].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailCharacter.route)
        composeTestRule.onNodeWithText(HSRCharacterData.dummyHSRCharacter[2].name).assertIsDisplayed()
    }

    @Test
    fun navHost_bottomNavigationIsWorking() {
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithStringId(R.string.menu_profile).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

}