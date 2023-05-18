package com.example.features.sharedfinance.login.presentation

sealed class LoginEvent {
    data class EnteredLogin(val value: String): LoginEvent()
    data class EnteredPassword(val value: String): LoginEvent()
    object LogIn: LoginEvent()
}