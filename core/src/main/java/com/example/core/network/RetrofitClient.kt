package com.example.core.network

import android.util.Log
import com.example.core.shared_preferences.data.StorageManager
import com.example.core.utils.Constants
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RetrofitClient @Inject constructor(
    private val authorizationInterceptor: AuthorizationInterceptor
) {

    companion object {
        const val CONNECT_TIMEOUT_SECONDS = 30L
        const val READ_TIMEOUT_SECONDS = 60L
        const val WRITE_TIMEOUT_SECONDS = 60L
    }

    fun getAuthorizedInstance(): Retrofit {
        return createRetrofit(createOkHttpClient(authorized = true))
    }

    fun getUnauthorizedInstance(): Retrofit {
        return createRetrofit(createOkHttpClient(authorized = false))
    }

    private fun createRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BACKEND_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }

    private fun createOkHttpClient(authorized: Boolean): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                if (authorized) {
                    addInterceptor(authorizationInterceptor)
                }
                addInterceptor(createLoggingInterceptor())
            }
            .build()
    }

    private fun createLoggingInterceptor(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return loggingInterceptor
    }
}
