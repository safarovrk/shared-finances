package com.example.features.sharedfinance.registration.presentation

sealed class RegistrationEvent {
    data class EnteredLogin(val value: String): RegistrationEvent()
    data class EnteredPassword(val value: String): RegistrationEvent()
    object Register: RegistrationEvent()
}
