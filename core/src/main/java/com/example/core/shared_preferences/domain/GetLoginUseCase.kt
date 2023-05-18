package com.example.core.shared_preferences.domain

import com.example.core.shared_preferences.data.StorageManager
import javax.inject.Inject

class GetLoginUseCase @Inject constructor(
    private val storageManager: StorageManager
) {
    suspend fun invoke(): String {
        return storageManager.getValue(StorageManager.LOGIN_KEY)
    }
}