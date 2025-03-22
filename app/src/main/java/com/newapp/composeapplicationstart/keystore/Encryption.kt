package com.newapp.composeapplicationstart.keystore

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

private const val KEY_ALIAS = "APIKeyEncryptionKey"
private const val ANDROID_KEYSTORE = "AndroidKeyStore"

fun generateKey() {
    val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
    val keyGenParameterSpec = KeyGenParameterSpec.Builder(
        KEY_ALIAS,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    )
        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .setUserAuthenticationRequired(false)  // No need for biometric auth in this case
        .build()

    keyGenerator.init(keyGenParameterSpec)
    keyGenerator.generateKey()
}

fun encryptAPIKey(apiKey: String): Pair<String, ByteArray>? {
    val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
    val keyEntry = keyStore.getEntry(KEY_ALIAS, null) as? KeyStore.SecretKeyEntry
    val secretKey = keyEntry?.secretKey ?: return null

    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    cipher.init(Cipher.ENCRYPT_MODE, secretKey)

    val iv = cipher.iv  // Store IV separately for decryption
    val encryptedBytes = cipher.doFinal(apiKey.toByteArray())

    val encryptedBase64 = Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    return Pair(encryptedBase64, iv)
}