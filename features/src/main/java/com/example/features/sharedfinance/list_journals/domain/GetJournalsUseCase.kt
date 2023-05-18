package com.example.features.sharedfinance.list_journals.domain

import com.example.core.utils.Resource
import com.example.features.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class GetJournalsUseCase @Inject constructor(
    private val repository: ListJournalsRepository
) {
    operator fun invoke(journalsRequest: JournalsRequest): Flow<Resource<Response<List<Journal>>>> = flow {
        try {
            emit(Resource.Loading<Response<List<Journal>>>())
            val response = repository.getJournals(journalsRequest)
            emit(Resource.Success<Response<List<Journal>>>(response))
        } catch (e: HttpException) {
            emit(Resource.Error<Response<List<Journal>>>(R.string.unexpected_error))
        } catch (e: IOException) {
            emit(Resource.Error<Response<List<Journal>>>(R.string.no_internet_connection))
        }
    }
}