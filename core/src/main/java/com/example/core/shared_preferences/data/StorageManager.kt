package com.example.core.shared_preferences.data

interface StorageManager {

    suspend fun setValue(key: String, value: String): Boolean

    suspend fun getValue(key: String): String

    suspend fun deleteValue(key: String): Boolean

    companion object {
        const val PREF_FILE_NAME = "encrypted_shared_pref"
        const val JOURNAL_NAME_KEY = "journal_name"
        const val LOGIN_KEY = "login"
        const val TOKEN_KEY = "token"
        const val ROLE_KEY = "role"
    }
}