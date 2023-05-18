package com.example.features.sharedfinance.home.charts

import com.example.features.sharedfinance.home.journal.domain.response_entity.Note

data class ChartState (
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessageId: Int = 0
)