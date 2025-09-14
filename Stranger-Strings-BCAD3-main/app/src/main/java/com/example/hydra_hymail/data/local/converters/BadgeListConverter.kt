package com.example.hydra_hymail.data.local.converters

import androidx.room.TypeConverter
import com.example.hydra_hymail.model.Badge
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Converts a List<Badge> to JSON for Room, and back again.
 */
class BadgeListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromBadgeList(badges: List<Badge>): String {
        return gson.toJson(badges)
    }

    @TypeConverter
    fun toBadgeList(data: String): List<Badge> {
        val listType = object : TypeToken<List<Badge>>() {}.type
        return gson.fromJson(data, listType) ?: emptyList()
    }
}
