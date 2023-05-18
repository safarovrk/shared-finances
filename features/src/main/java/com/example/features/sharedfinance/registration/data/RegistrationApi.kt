package com.example.features.sharedfinance.registration.data

import com.example.features.sharedfinance.registration.domain.RegisterRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body

import retrofit2.http.POST

interface RegistrationApi {

    @POST("/registration")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<ResponseBody>

}