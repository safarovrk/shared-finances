package com.example.features.sharedfinance.invitations.domain

import com.example.core.utils.Resource
import com.example.features.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class ChoiceInvitationUseCase @Inject constructor(
    private val repository: InvitationsRepository
) {
    operator fun invoke(choiceRequest: ChoiceRequest, accepting: Boolean): Flow<Resource<Response<ResponseBody>>> = flow {
        try {
            emit(Resource.Loading())
            val response: Response<ResponseBody> =
                if (accepting) repository.acceptInvitation(choiceRequest)
                else repository.declineInvitation(choiceRequest)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(R.string.unexpected_error))
        } catch (e: IOException) {
            emit(Resource.Error(R.string.no_internet_connection))
        }
    }
}