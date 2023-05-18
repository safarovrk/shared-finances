package com.example.features.sharedfinance.invitations.data

import com.example.features.sharedfinance.invitations.domain.ChoiceRequest
import com.example.features.sharedfinance.invitations.domain.Invitation
import com.example.features.sharedfinance.invitations.domain.InvitationsRequest
import com.example.features.sharedfinance.list_journals.domain.JournalsRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface InvitationsApi {
    @POST("/private/invitations")
    suspend fun getInvitations(@Body invitationsRequest: InvitationsRequest.Login): Response<List<Invitation>>

    @POST("/private/accept")
    suspend fun acceptInvitation(@Body choiceRequest: ChoiceRequest): Response<ResponseBody>

    @POST("/private/decline")
    suspend fun declineInvitation(@Body choiceRequest: ChoiceRequest): Response<ResponseBody>
}