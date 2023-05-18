package com.example.features.sharedfinance.home.journal.presentation
import com.example.features.sharedfinance.home.categories.domain.Category

data class CategoryListState(
    val categories: List<Category> = emptyList()
)