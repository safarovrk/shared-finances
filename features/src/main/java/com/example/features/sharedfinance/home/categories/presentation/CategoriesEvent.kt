package com.example.features.sharedfinance.home.categories.presentation

sealed class CategoriesEvent {
    object CreationClick: CategoriesEvent()
    data class OnDeleteClicked(val categoryName: String): CategoriesEvent()
    object OnCreationDismiss: CategoriesEvent()
    data class OnCreationSubmit(val categoryName: String): CategoriesEvent()
    data class EnteredCategoryName(val value: String): CategoriesEvent()
}