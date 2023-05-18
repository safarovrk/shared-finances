package com.example.core.shared_preferences.domain

import com.example.core.shared_preferences.data.StorageManager
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val storageManager: StorageManager
) {
    suspend operator fun invoke() {
        storageManager.deleteValue(StorageManager.JOURNAL_NAME_KEY)
        storageManager.deleteValue(StorageManager.LOGIN_KEY)
        storageManager.deleteValue(StorageManager.TOKEN_KEY)
        storageManager.deleteValue(StorageManager.ROLE_KEY)
    }
}