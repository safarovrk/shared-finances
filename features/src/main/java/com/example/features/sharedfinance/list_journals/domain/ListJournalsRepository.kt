package com.example.features.sharedfinance.list_journals.domain

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body

interface ListJournalsRepository {

    suspend fun getJournals(journalsRequest: JournalsRequest): Response<List<Journal>>

    suspend fun createJournal(createJournalRequest: CreateJournalRequest): Response<ResponseBody>

    suspend fun getUserRole(roleRequest: RoleRequest): Long
}