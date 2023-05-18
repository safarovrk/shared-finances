package com.example.features.sharedfinance.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.core.navigation.Screen
import com.example.core.theme.CustomTheme
import com.example.features.sharedfinance.list_journals.presentation.ListJournalsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashScreenViewModel = hiltViewModel()
) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(1000)
        viewModel.checkAuthStatus()
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is SplashScreenViewModel.UiEvent.Unauthorized -> {
                    navController.navigate(Screen.Login.screenName) {
                        popUpTo(Screen.Splash.screenName) {
                            inclusive = true
                        }
                    }
                }
                is SplashScreenViewModel.UiEvent.AuthorizedWithNoJournal -> {
                    navController.navigate(Screen.ListJournals.screenName) {
                        popUpTo(Screen.Splash.screenName) {
                            inclusive = true
                        }
                    }
                }
                is SplashScreenViewModel.UiEvent.AuthorizedWithJournal -> {
                    navController.navigate(Screen.Home.screenName) {
                        popUpTo(Screen.Splash.screenName) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
    Splash(alpha = alphaAnim.value)
}

@Composable
fun Splash(alpha: Float) {
    Box(
        modifier = Modifier
            .background(CustomTheme.colors.background.primary)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(120.dp)
                .alpha(alpha = alpha),
            imageVector = Icons.Default.List,
            contentDescription = "Logo Icon",
            tint = Color.White
        )
    }
}