package com.example.features.sharedfinance.registration.data

import com.example.features.sharedfinance.registration.domain.RegisterRequest
import com.example.features.sharedfinance.registration.domain.RegistrationRepository
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val api: RegistrationApi
) : RegistrationRepository {

    override suspend fun register(registerRequest: RegisterRequest): Response<ResponseBody> {
        return api.register(registerRequest)
    }
}