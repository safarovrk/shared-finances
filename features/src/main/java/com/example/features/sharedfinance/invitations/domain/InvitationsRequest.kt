package com.example.features.sharedfinance.invitations.domain

data class InvitationsRequest(
    val login: Login
) {
    data class Login(
        val login: String
    )
}
