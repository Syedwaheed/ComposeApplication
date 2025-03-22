package com.newapp.composeapplicationstart.di

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.newapp.composeapplicationstart.presentation.utils.EncryptedSharedPrefKeyValue
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EncryptedSharedPref {
//    @Provides
//    @Singleton
//    fun provideEncryptedSharedPref(@ApplicationContext context:Context): EncryptedSharedPreferences{
//        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
//        return EncryptedSharedPreferences.create(
//            "secure_prefs",
//            masterKeyAlias,
//            context,
//            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//        ) as EncryptedSharedPreferences
//    }

    @Provides
    fun provideEncryptedSharedPrefKeyValue(@ApplicationContext context: Context) =
        EncryptedSharedPrefKeyValue(context)
}