package com.example.features.sharedfinance.invitations.domain

import okhttp3.ResponseBody
import retrofit2.Response


interface InvitationsRepository {
    suspend fun getInvitations(invitationsRequest: InvitationsRequest): Response<List<Invitation>>

    suspend fun acceptInvitation(choiceRequest: ChoiceRequest): Response<ResponseBody>

    suspend fun declineInvitation(choiceRequest: ChoiceRequest): Response<ResponseBody>
}