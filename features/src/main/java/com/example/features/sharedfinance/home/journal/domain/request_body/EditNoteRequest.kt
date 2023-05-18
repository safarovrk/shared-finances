package com.example.features.sharedfinance.home.journal.domain.request_body

data class EditNoteRequest(
    val id: Long,
    val date: String,
    val sum: Long,
    val category: String,
    val comment: String,
    val login: String
)
