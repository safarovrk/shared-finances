package com.example.features.sharedfinance.list_journals.data

import com.example.features.sharedfinance.list_journals.domain.CreateJournalRequest
import com.example.features.sharedfinance.list_journals.domain.Journal
import com.example.features.sharedfinance.list_journals.domain.JournalsRequest
import com.example.features.sharedfinance.list_journals.domain.RoleRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ListJournalsApi {

    @POST("/private/userJournals")
    suspend fun getJournals(@Body journalsRequest: JournalsRequest): Response<List<Journal>>

    @POST("/private/createJournal")
    suspend fun createJournal(@Body createJournalRequest: CreateJournalRequest): Response<ResponseBody>

    @POST("/private/userRole")
    suspend fun getUserRole(@Body roleRequest: RoleRequest): Long
}