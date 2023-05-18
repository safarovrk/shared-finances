package com.example.features.sharedfinance.home.categories.presentation

import com.example.features.sharedfinance.home.categories.domain.Category

data class CategoriesState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessageId: Int = 0,
    val isDialogShown: Boolean = false
)
