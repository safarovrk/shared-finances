package com.example.core.network

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.core.shared_preferences.data.StorageManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val storageManager: StorageManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {

        val token = storageManager.getValue(StorageManager.TOKEN_KEY)

        chain.proceed(
            chain.request()
                .newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        )
    }
}