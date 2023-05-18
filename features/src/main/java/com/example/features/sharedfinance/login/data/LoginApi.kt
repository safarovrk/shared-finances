package com.example.features.sharedfinance.login.data

import com.example.features.sharedfinance.login.domain.Token
import com.example.features.sharedfinance.login.domain.LoginRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("/login")
    suspend fun login(@Body loginRequestBody: LoginRequestBody): Response<Token>
}