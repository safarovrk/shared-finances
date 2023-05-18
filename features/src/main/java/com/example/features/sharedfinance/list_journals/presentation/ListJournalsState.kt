package com.example.features.sharedfinance.list_journals.presentation

import com.example.features.sharedfinance.list_journals.domain.Journal

data class ListJournalsState (
    val journals: List<Journal> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessageId: Int = 0,
    val isDialogShown: Boolean = false
)