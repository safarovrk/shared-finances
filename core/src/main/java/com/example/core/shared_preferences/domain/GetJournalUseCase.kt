package com.example.core.shared_preferences.domain

import com.example.core.shared_preferences.data.StorageManager
import javax.inject.Inject

class GetJournalUseCase @Inject constructor(
    private val storageManager: StorageManager
) {
    suspend operator fun invoke(): String {
        return storageManager.getValue(StorageManager.JOURNAL_NAME_KEY)
    }
}