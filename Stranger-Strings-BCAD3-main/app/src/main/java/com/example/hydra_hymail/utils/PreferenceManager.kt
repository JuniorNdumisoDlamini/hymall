package com.example.hydra_hymail.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Wrapper around SharedPreferences for saving user settings locally.
 * Great for things like "Dark Mode", "Preferred Language", "Remember Me".
 */
object PreferenceManager {
    private const val PREFS_NAME = "HyMallPrefs"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveString(context: Context, key: String, value: String) {
        getPrefs(context).edit().putString(key, value).apply()
    }

    fun getString(context: Context, key: String, defaultValue: String = ""): String {
        return getPrefs(context).getString(key, defaultValue) ?: defaultValue
    }

    fun saveBoolean(context: Context, key: String, value: Boolean) {
        getPrefs(context).edit().putBoolean(key, value).apply()
    }

    fun getBoolean(context: Context, key: String, defaultValue: Boolean = false): Boolean {
        return getPrefs(context).getBoolean(key, defaultValue)
    }
}
