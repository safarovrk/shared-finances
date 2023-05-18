package com.example.core.shared_preferences.domain

import com.example.core.shared_preferences.data.StorageManager
import javax.inject.Inject

class ChangeJournalUseCase @Inject constructor(
    private val storageManager: StorageManager
) {
    suspend operator fun invoke() {
        storageManager.deleteValue(StorageManager.JOURNAL_NAME_KEY)
        storageManager.deleteValue(StorageManager.ROLE_KEY)
    }
}