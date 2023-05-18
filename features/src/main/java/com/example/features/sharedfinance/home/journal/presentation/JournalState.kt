package com.example.features.sharedfinance.home.journal.presentation

import com.example.features.sharedfinance.home.journal.domain.response_entity.Note

data class JournalState (
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessageId: Int = 0,
    val isCreationDialogShown: Boolean = false,
    val isEditionDialogShown: Boolean = false
)