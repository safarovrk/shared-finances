package com.example.features.sharedfinance.invitations.presentation

import com.example.features.sharedfinance.invitations.domain.Invitation

data class InvitationsState (
    val invitations: List<Invitation> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessageId: Int = 0
)
