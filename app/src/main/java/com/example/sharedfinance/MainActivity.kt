package com.example.sharedfinance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import com.example.core.theme.AppTheme
import com.example.core.theme.CustomTheme
import com.example.features.sharedfinance.root.ui.RootUI
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val systemUiController: SystemUiController = rememberSystemUiController()

                systemUiController.isStatusBarVisible = false
                systemUiController.setStatusBarColor(
                    CustomTheme.colors.background.primary,
                    !isSystemInDarkTheme()
                )
                systemUiController.setNavigationBarColor(
                    CustomTheme.colors.background.primary,
                    !isSystemInDarkTheme()
                )
                RootUI()
            }
        }
    }
}
