package com.example.features.sharedfinance.home.journal.domain.request_body

data class DeleteNoteRequest (
    val idNote: Long,
    val login: String,
    val journalName: String
)