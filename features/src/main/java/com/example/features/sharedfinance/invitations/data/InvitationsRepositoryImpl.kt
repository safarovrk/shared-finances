package com.example.features.sharedfinance.invitations.data

import com.example.features.sharedfinance.invitations.domain.ChoiceRequest
import com.example.features.sharedfinance.invitations.domain.Invitation
import com.example.features.sharedfinance.invitations.domain.InvitationsRepository
import com.example.features.sharedfinance.invitations.domain.InvitationsRequest
import com.example.features.sharedfinance.list_journals.domain.JournalsRequest
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class InvitationsRepositoryImpl @Inject constructor(
    private val api: InvitationsApi
) : InvitationsRepository {

    override suspend fun getInvitations(invitationsRequest: InvitationsRequest): Response<List<Invitation>> {
        return api.getInvitations(invitationsRequest.login)
    }

    override suspend fun acceptInvitation(choiceRequest: ChoiceRequest): Response<ResponseBody> {
        return api.acceptInvitation(choiceRequest)
    }

    override suspend fun declineInvitation(choiceRequest: ChoiceRequest): Response<ResponseBody> {
        return api.declineInvitation(choiceRequest)
    }
}