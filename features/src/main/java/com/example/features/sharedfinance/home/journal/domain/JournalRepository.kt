package com.example.features.sharedfinance.home.journal.domain

import com.example.features.sharedfinance.home.journal.domain.request_body.CreateNoteRequest
import com.example.features.sharedfinance.home.journal.domain.request_body.DeleteNoteRequest
import com.example.features.sharedfinance.home.journal.domain.request_body.EditNoteRequest
import com.example.features.sharedfinance.home.journal.domain.request_body.NoteListRequest
import com.example.features.sharedfinance.home.journal.domain.response_entity.Note
import okhttp3.ResponseBody
import retrofit2.Response

interface JournalRepository {

    suspend fun getNoteList(noteListRequest: NoteListRequest): Response<List<Note>>

    suspend fun createNote(createNoteRequest: CreateNoteRequest): Response<ResponseBody>

    suspend fun deleteNote(deleteNoteRequest: DeleteNoteRequest): Response<ResponseBody>

    suspend fun editNote(editNoteRequest: EditNoteRequest): Response<ResponseBody>
}