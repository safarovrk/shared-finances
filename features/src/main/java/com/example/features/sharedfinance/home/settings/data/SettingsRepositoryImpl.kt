package com.example.features.sharedfinance.home.settings.data

import com.example.features.sharedfinance.home.settings.domain.ExitJournalRequest
import com.example.features.sharedfinance.home.settings.domain.InviteRequest
import com.example.features.sharedfinance.home.settings.domain.SettingsRepository
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val api: SettingsApi
) : SettingsRepository {
    override suspend fun inviteUser(inviteRequest: InviteRequest): Response<ResponseBody> {
        return api.inviteUser(inviteRequest)
    }

    override suspend fun exitJournal(exitJournalRequest: ExitJournalRequest): Response<ResponseBody> {
        return api.exitJournal(exitJournalRequest)
    }
}