package com.example.features.sharedfinance.home.categories.domain

data class CreateDeleteCategoryRequest(
    val category: String,
    val login: String,
    val journalName: String
)
