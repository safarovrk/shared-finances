package com.example.core.shared_preferences.domain

import com.example.core.shared_preferences.data.StorageManager
import java.lang.NumberFormatException
import javax.inject.Inject

class GetRoleUseCase @Inject constructor(
    private val storageManager: StorageManager
) {
    suspend fun invoke(): Long {
        return try {
            storageManager.getValue(StorageManager.ROLE_KEY).toLong()
        } catch (e: NumberFormatException) {
            -1
        }
    }
}