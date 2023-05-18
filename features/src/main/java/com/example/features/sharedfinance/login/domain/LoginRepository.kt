package com.example.features.sharedfinance.login.domain

import retrofit2.Response

interface LoginRepository {
    suspend fun login(loginRequestBody: LoginRequestBody): Response<Token>
}