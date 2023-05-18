package com.example.features.sharedfinance.list_journals.presentation


sealed class ListJournalsEvent {
    data class JournalChosen(val journalName: String): ListJournalsEvent()
    object CreationClick: ListJournalsEvent()
    object OnCreationDismiss: ListJournalsEvent()
    data class OnCreationSubmit(val journalName: String): ListJournalsEvent()
    data class EnteredJournalName(val value: String): ListJournalsEvent()
    object OnInvitationsClicked: ListJournalsEvent()
    object OnLogoutClicked: ListJournalsEvent()
}
