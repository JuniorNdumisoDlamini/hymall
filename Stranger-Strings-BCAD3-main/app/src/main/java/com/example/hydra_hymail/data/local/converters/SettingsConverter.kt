package com.example.hydra_hymail.data.local.converters

import androidx.room.TypeConverter
import com.example.hydra_hymail.model.Settings
import com.google.gson.Gson

/**
 * Converts Settings object to JSON for Room, and back again.
 */
class SettingsConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromSettings(settings: Settings): String {
        return gson.toJson(settings)
    }

    @TypeConverter
    fun toSettings(data: String): Settings {
        return gson.fromJson(data, Settings::class.java) ?: Settings()
    }
}
