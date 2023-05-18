package com.example.features.sharedfinance.home.journal.domain.request_body

data class CreateNoteRequest(
    val date: String,
    val sum: Long,
    val category: String,
    val comment: String,
    val journalName: String,
)
