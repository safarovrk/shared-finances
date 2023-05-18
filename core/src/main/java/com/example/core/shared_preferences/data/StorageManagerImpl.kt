package com.example.core.shared_preferences.data

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.core.shared_preferences.data.StorageManager.Companion.PREF_FILE_NAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class StorageManagerImpl(
    context: Context
) : StorageManager {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPref: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        PREF_FILE_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override suspend fun setValue(key: String, value: String): Boolean = withContext(Dispatchers.IO) {
        return@withContext sharedPref.edit().putString(key, value).commit()
    }
    override suspend fun getValue(key: String): String = withContext(Dispatchers.IO) {
        return@withContext sharedPref.getString(key, null) ?: ""
    }
    override suspend fun deleteValue(key: String): Boolean = withContext(Dispatchers.IO) {
        return@withContext sharedPref.edit().putString(key, null).commit()
    }
}