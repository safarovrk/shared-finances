package com.example.features.sharedfinance.home.settings.domain

data class InviteRequest(
    val journalName: String,
    val login: String,
    val role: String,
    val admin: String
)
