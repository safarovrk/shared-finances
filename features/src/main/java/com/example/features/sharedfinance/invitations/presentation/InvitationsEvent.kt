package com.example.features.sharedfinance.invitations.presentation

sealed class InvitationsEvent {
    data class OnAcceptClicked(val journalName: String): InvitationsEvent()
    data class OnDeclineClicked(val journalName: String): InvitationsEvent()

}