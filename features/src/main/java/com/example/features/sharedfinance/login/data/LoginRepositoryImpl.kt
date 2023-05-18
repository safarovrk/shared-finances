package com.example.features.sharedfinance.login.data

import com.example.features.sharedfinance.login.domain.Token
import com.example.features.sharedfinance.login.domain.LoginRepository
import com.example.features.sharedfinance.login.domain.LoginRequestBody
import retrofit2.Response
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api: LoginApi
) : LoginRepository {

    override suspend fun login(loginRequestBody: LoginRequestBody): Response<Token> {
        return api.login(loginRequestBody)
    }
}