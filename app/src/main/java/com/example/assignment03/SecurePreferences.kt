/*
 * Course: MAD302-01 Android Development
 * Assignment: Assignment 3 - Smart Utility App
 * Student Name: Darshilkumar Karkar
 * Student ID: A00203357
 * Date of Submission: 2026-04-17
 * Description: Secure storage wrapper using AndroidX Security EncryptedSharedPreferences.
 *              Ensures sensitive data is never stored in plain text on the device.
 */

package com.example.assignment03

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * SecurePreferences wraps AndroidX EncryptedSharedPreferences to store sensitive
 * data (like user tokens or settings) with AES-256 encryption.
 *
 * Usage: All sensitive data MUST go through this class — never use plain
 * SharedPreferences for sensitive information.
 *
 * @param context Application or Activity context.
 */
class SecurePreferences(context: Context) {

    // MasterKey uses AES256-GCM, stored in Android Keystore (hardware-backed when available)
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    // EncryptedSharedPreferences encrypts both keys and values
    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",           // File name for encrypted prefs file
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    /**
     * Saves a String value securely under the given key.
     *
     * @param key The preference key (not visible to other apps).
     * @param value The sensitive value to store encrypted.
     */
    fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    /**
     * Retrieves a previously saved String value.
     *
     * @param key The preference key to look up.
     * @param default Returned if the key does not exist.
     * @return The decrypted stored value, or [default] if not found.
     */
    fun getString(key: String, default: String = ""): String {
        return sharedPreferences.getString(key, default) ?: default
    }

    /**
     * Removes a single key from secure storage.
     *
     * @param key The preference key to delete.
     */
    fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    companion object {
        // Key constants — centralizing prevents typos across the app
        const val KEY_LAST_SEARCH = "last_search_city"
        const val KEY_USER_TOKEN = "user_auth_token"
    }
}