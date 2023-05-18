package com.example.features.sharedfinance.invitations.domain

data class Invitation(
    val journalName: JournalName,
    val role: Role
) {
    data class JournalName(
        val journalName: String
    )
    data class Role(
        val name: String
    )
}
