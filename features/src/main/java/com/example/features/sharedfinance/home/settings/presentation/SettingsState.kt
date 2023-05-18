package com.example.features.sharedfinance.home.settings.presentation

import com.example.features.sharedfinance.home.categories.domain.Category

data class SettingsState(
    val errorMessageId: Int = 0,
    val isDialogShown: Boolean = false
)
