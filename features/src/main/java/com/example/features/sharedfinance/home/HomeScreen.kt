package com.example.features.sharedfinance.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core.navigation.Router
import com.example.core.navigation.Screen
import com.example.core.theme.CustomTheme
import com.example.features.sharedfinance.home.categories.presentation.CategoriesScreen
import com.example.features.sharedfinance.home.charts.ChartsScreen
import com.example.features.sharedfinance.home.journal.presentation.JournalScreen
import com.example.features.sharedfinance.home.settings.presentation.SettingsScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(externalRouter: Router) {
    val navController = rememberNavController()
    val bottomItems = listOf(Screen.Journal, Screen.Categories, Screen.Chart, Screen.Settings)

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
            /*BottomNavigation {
                bottomItems.forEach { screen ->
                    BottomNavigationItem(
                        selected = false,
                        onClick = {
                            navController.navigate(screen.screenName) {
                                launchSingleTop = true
                            }
                        },
                        label = { Text(stringResource(id = screen.titleResourceId)) },
                        icon = {

                        })
                }
            }*/
        }
    ) {
        NavHost(navController = navController, startDestination = Screen.Journal.screenName) {
            composable(Screen.Journal.screenName) { JournalScreen(navController) }
            composable(Screen.Categories.screenName) { CategoriesScreen(navController) }
            composable(Screen.Chart.screenName) { ChartsScreen() }
            composable(Screen.Settings.screenName) { SettingsScreen(externalRouter, navController) }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        Screen.Journal,
        Screen.Categories,
        Screen.Chart,
        Screen.Settings
    )

    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination


    Row(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp)
            .background(Color.Transparent)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }

}

@Composable
fun AddItem(
    screen: Screen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.screenName } == true

    val background =
        if (selected) CustomTheme.colors.icon.disabled.copy(alpha = 0.7f) else Color.Transparent
    //if (selected) CustomTheme.colors.button.primary.copy(alpha = 0.6f) else Color.Transparent

    val contentColor =
        if (selected) CustomTheme.colors.icon.contrast else CustomTheme.colors.icon.disabled

    Box(
        modifier = Modifier
            .height(40.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(background)
            .clickable(onClick = {
                navController.navigate(screen.screenName) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            })
    ) {
        Row(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(id = if (selected) screen.focused_icon else screen.icon),
                contentDescription = "icon",
                tint = contentColor
            )
            AnimatedVisibility(visible = selected) {
                Text(
                    text = stringResource(screen.titleResourceId),
                    color = contentColor
                )
            }
        }
    }
}