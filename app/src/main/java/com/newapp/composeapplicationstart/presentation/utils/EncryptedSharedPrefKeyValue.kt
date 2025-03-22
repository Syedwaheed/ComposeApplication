package com.newapp.composeapplicationstart.presentation.utils

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.newapp.composeapplicationstart.BuildConfig
import javax.inject.Inject

open class EncryptedSharedPrefKeyValue @Inject constructor(
    context: Context
) {
    companion object {
        const val ACCESSTOKEN = "access_token"
    }
    private val masterKeys = MasterKeys.getOrCreate(
        MasterKeys.AES256_GCM_SPEC
    )
    private val encryptedSharedPreferences: EncryptedSharedPreferences = EncryptedSharedPreferences.create(
        "secure_shared_prefs",
        masterKeys,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    ) as EncryptedSharedPreferences

    open fun addAccessToken(){
        encryptedSharedPreferences.edit {
            putString(ACCESSTOKEN,"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5NzYwZWM2MzQ2ZGU4ZjhiODc2OTVhNGVmNDFkNzZhNCIsIm5iZiI6MTUwMjc3OTY3MC4yOTksInN1YiI6IjU5OTI5OTEzYzNhMzY4NDBkNDAwOGVlNyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.IPQzTLy7NBX7RIDNXD_xNW73_7al_WaYQmMRMWbLdqI")
        }
    }
    open fun getAccessToken() = encryptedSharedPreferences.getString(ACCESSTOKEN,"")

}