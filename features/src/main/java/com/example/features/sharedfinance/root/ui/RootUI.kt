package com.example.features.sharedfinance.root.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.core.navigation.Screen
import com.example.core.navigation.createExternalRouter
import com.example.core.navigation.navigate
import com.example.core.theme.CustomTheme
import com.example.features.sharedfinance.home.HomeScreen
import com.example.features.sharedfinance.invitations.presentation.InvitationsScreen
import com.example.features.sharedfinance.list_journals.presentation.ListJournalsScreen
import com.example.features.sharedfinance.registration.presentation.RegistrationScreen

import com.example.features.sharedfinance.login.presentation.LoginScreen
import com.example.features.sharedfinance.splash.SplashScreen
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun RootUI() {


    Surface(color = CustomTheme.colors.background.primary) {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Screen.Splash.screenName) {
            composable(Screen.Splash.screenName) {
                SplashScreen(navController)
            }
            composable(Screen.Login.screenName) {
                LoginScreen(navController)
            }
            composable(Screen.Registration.screenName) {
                RegistrationScreen(navController)
            }
            composable(Screen.ListJournals.screenName) {
                ListJournalsScreen(navController)
            }
            composable(Screen.Invitations.screenName) {
                InvitationsScreen(navController)
            }
            composable(Screen.Home.screenName) {
                HomeScreen(
                    createExternalRouter { screen, params ->
                        navController.navigate(screen, params)
                    }
                )
            }

        }
    }
}

/*
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.screenName
    ) {
        composable(route = Screen.Login.screenName) { LoginScreen() }
        composable(route = Screen.Registration.screenName) { RegistrationScreen() }
        composable(route = Screen.ListJournals.screenName) { ListJournalsScreen() }
        composable(route = Screen.JournalCreation.screenName) { JournalCreationScreen() }
        composable(route = Screen.Home.screenName) {
            HomeScreen(createExternalRouter { screen, params ->
                navController.navigate(screen, params)
            })
        }
        composable(route = Screen.Categories.screenName) { CategoriesScreen() }
        composable(route = Screen.CategoryCreation.screenName) { CategoryCreationScreen() }
        composable(route = Screen.Settings.screenName) { SettingsScreen() }
        composable(route = Screen.Charts.screenName) { ChartsScreen() }
        composable(route = Screen.Invitations.screenName) { InvitationsScreen() }
        composable(route = Screen.UserInvitation.screenName) { UserInvitationScreen() }

    }
}
*/
