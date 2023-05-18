package com.example.features.sharedfinance.home.journal.data

import com.example.features.sharedfinance.home.journal.domain.JournalRepository
import com.example.features.sharedfinance.home.journal.domain.request_body.CreateNoteRequest
import com.example.features.sharedfinance.home.journal.domain.request_body.DeleteNoteRequest
import com.example.features.sharedfinance.home.journal.domain.request_body.EditNoteRequest
import com.example.features.sharedfinance.home.journal.domain.request_body.NoteListRequest
import com.example.features.sharedfinance.home.journal.domain.response_entity.Note
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class JournalRepositoryImpl @Inject constructor(
    private val api: JournalApi
) : JournalRepository {
    override suspend fun getNoteList(noteListRequest: NoteListRequest): Response<List<Note>> {
        return api.getNoteList(noteListRequest)
    }

    override suspend fun createNote(createNoteRequest: CreateNoteRequest): Response<ResponseBody> {
        return api.createNote(createNoteRequest)
    }

    override suspend fun deleteNote(deleteNoteRequest: DeleteNoteRequest): Response<ResponseBody> {
        return api.deleteNote(deleteNoteRequest)
    }

    override suspend fun editNote(editNoteRequest: EditNoteRequest): Response<ResponseBody> {
        return api.editNote(editNoteRequest)
    }

}