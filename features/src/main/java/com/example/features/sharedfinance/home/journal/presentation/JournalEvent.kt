package com.example.features.sharedfinance.home.journal.presentation

import com.example.features.sharedfinance.home.journal.domain.response_entity.Note

sealed class JournalEvent {

    object OnCreateClicked : JournalEvent()
    data class OnEditClicked(val note: Note) : JournalEvent()
    data class OnDeleteClicked(val idNote: Long) : JournalEvent()

    data class EnteredSum(val value: String) : JournalEvent()
    data class EnteredCategory(val value: String) : JournalEvent()
    data class EnteredComment(val value: String) : JournalEvent()
    data class OnCreationSubmit(
        val sum: String,
        val category: String,
        val comment: String
        ) : JournalEvent()
    object OnCreationDismiss : JournalEvent()

    data class EditedSum(val value: String) : JournalEvent()
    data class EditedCategory(val value: String) : JournalEvent()
    data class EditedComment(val value: String) : JournalEvent()
    data class OnEditionSubmit(
        val id: Long,
        val date: String,
        val sum: String,
        val category: String,
        val comment: String
        ) : JournalEvent()
    object OnEditionDismiss : JournalEvent()

}