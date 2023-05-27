package com.example.features.sharedfinance.home.settings.domain

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SettingsRepository {
    suspend fun inviteUser(inviteRequest: InviteRequest): Response<ResponseBody>

    suspend fun exitJournal(exitJournalRequest: ExitJournalRequest): Response<ResponseBody>
}