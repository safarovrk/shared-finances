package com.example.features.sharedfinance.login.domain

import com.example.core.utils.Resource
import com.example.features.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    operator fun invoke(loginRequestBody: LoginRequestBody): Flow<Resource<Response<Token>>> = flow {
        try {
            emit(Resource.Loading<Response<Token>>())
            val response = repository.login(loginRequestBody)
            emit(Resource.Success<Response<Token>>(response))
        } catch (e: HttpException) {
            emit(Resource.Error<Response<Token>>(R.string.unexpected_error))
        } catch (e: IOException) {
            emit(Resource.Error<Response<Token>>(R.string.no_internet_connection))
        }
    }
}