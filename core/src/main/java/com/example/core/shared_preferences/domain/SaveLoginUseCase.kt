package com.example.core.shared_preferences.domain

import com.example.core.shared_preferences.data.StorageManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveLoginUseCase @Inject constructor(
    private val storageManager: StorageManager
) {
    operator fun invoke(login: String): Flow<Boolean> = flow {
        emit(storageManager.setValue(StorageManager.LOGIN_KEY, login))
    }
}