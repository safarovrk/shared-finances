package com.example.features.sharedfinance.home.journal.domain

import com.example.core.utils.Resource
import com.example.features.R
import com.example.features.sharedfinance.home.journal.domain.request_body.CreateNoteRequest
import com.example.features.sharedfinance.home.settings.domain.InviteRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class CreateNoteUseCase @Inject constructor(
    private val repository: JournalRepository
) {
    operator fun invoke(createNoteRequest: CreateNoteRequest): Flow<Resource<Response<ResponseBody>>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.createNote(createNoteRequest)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(R.string.unexpected_error))
        } catch (e: IOException) {
            emit(Resource.Error(R.string.no_internet_connection))
        }
    }
}