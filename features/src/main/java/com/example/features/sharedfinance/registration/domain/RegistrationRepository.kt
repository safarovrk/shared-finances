package com.example.features.sharedfinance.registration.domain

import okhttp3.ResponseBody
import retrofit2.Response

interface RegistrationRepository {

    suspend fun register(registerRequest: RegisterRequest): Response<ResponseBody>

}