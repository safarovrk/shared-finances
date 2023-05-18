package com.example.features.sharedfinance.home.settings.presentation

sealed class SettingsEvent {
    object LogOutClicked : SettingsEvent()
    object InviteUserClicked : SettingsEvent()
    object InvitationsClicked : SettingsEvent()
    object ChangeJournalClicked : SettingsEvent()
    data class OnInvitationSubmit(val userName: String, val role: String): SettingsEvent()
    object OnInvitationDismiss : SettingsEvent()
    data class EnteredUserName(val value: String): SettingsEvent()
}
