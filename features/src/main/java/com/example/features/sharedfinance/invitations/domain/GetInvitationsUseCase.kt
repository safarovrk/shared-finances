package com.example.features.sharedfinance.invitations.domain

import com.example.core.utils.Resource
import com.example.features.R
import com.example.features.sharedfinance.list_journals.domain.CreateJournalRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class GetInvitationsUseCase @Inject constructor(
    private val repository: InvitationsRepository
) {
    operator fun invoke(invitationsRequest: InvitationsRequest): Flow<Resource<Response<List<Invitation>>>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getInvitations(invitationsRequest)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(R.string.unexpected_error))
        } catch (e: IOException) {
            emit(Resource.Error(R.string.no_internet_connection))
        }
    }
}