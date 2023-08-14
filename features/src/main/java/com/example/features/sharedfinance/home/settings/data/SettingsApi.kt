package com.example.features.sharedfinance.home.settings.data

import com.example.features.sharedfinance.home.categories.domain.Category
import com.example.features.sharedfinance.home.categories.domain.ListCategoryRequest
import com.example.features.sharedfinance.home.settings.domain.ExitJournalRequest
import com.example.features.sharedfinance.home.settings.domain.InviteRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SettingsApi {
    @POST("/private/addUser")
    suspend fun inviteUser(@Body inviteRequest: InviteRequest): Response<ResponseBody>

    @POST("/private/exitJournal")
    suspend fun exitJournal(@Body exitJournalRequest: ExitJournalRequest): Response<ResponseBody>
}