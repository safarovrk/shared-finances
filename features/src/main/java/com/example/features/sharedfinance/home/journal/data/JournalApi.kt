package com.example.features.sharedfinance.home.journal.data

import com.example.features.sharedfinance.home.journal.domain.request_body.CreateNoteRequest
import com.example.features.sharedfinance.home.journal.domain.request_body.DeleteNoteRequest
import com.example.features.sharedfinance.home.journal.domain.request_body.EditNoteRequest
import com.example.features.sharedfinance.home.journal.domain.request_body.NoteListRequest
import com.example.features.sharedfinance.home.journal.domain.response_entity.Note
import com.example.features.sharedfinance.home.settings.domain.InviteRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface JournalApi {
    @POST("/private/listNote")
    suspend fun getNoteList(@Body noteListRequest: NoteListRequest): Response<List<Note>>

    @POST("/private/addNote")
    suspend fun createNote(@Body createNoteRequest: CreateNoteRequest): Response<ResponseBody>

    @POST("/private/deleteNote")
    suspend fun deleteNote(@Body deleteNoteRequest: DeleteNoteRequest): Response<ResponseBody>

    @POST("/private/updateNote")
    suspend fun editNote(@Body editNoteRequest: EditNoteRequest): Response<ResponseBody>

}