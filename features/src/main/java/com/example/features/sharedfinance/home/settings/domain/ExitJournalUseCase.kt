package com.example.features.sharedfinance.home.settings.domain

import com.example.core.utils.Resource
import com.example.features.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class ExitJournalUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke(exitJournalRequest: ExitJournalRequest): Flow<Resource<Response<ResponseBody>>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.exitJournal(exitJournalRequest)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(R.string.unexpected_error))
        } catch (e: IOException) {
            emit(Resource.Error(R.string.no_internet_connection))
        }
    }
}