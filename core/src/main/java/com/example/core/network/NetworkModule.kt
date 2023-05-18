package com.example.core.network

import android.content.Context
import com.example.core.network.RetrofitClient.Companion.CONNECT_TIMEOUT_SECONDS
import com.example.core.network.RetrofitClient.Companion.READ_TIMEOUT_SECONDS
import com.example.core.network.RetrofitClient.Companion.WRITE_TIMEOUT_SECONDS
import com.example.core.shared_preferences.data.StorageManager
import com.example.core.shared_preferences.data.StorageManagerImpl
import com.example.core.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideStorageManager(context: Context): StorageManager {
        return StorageManagerImpl(context)
    }

    @Singleton
    @Provides
    fun provideAuthorizationInterceptor(storageManager: StorageManager): AuthorizationInterceptor {
        return AuthorizationInterceptor(storageManager)
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(authorizationInterceptor: AuthorizationInterceptor): RetrofitClient {
        return RetrofitClient(authorizationInterceptor)
    }

}