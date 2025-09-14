package com.example.hydra_hymail.model

import androidx.room.Entity


@Entity(tableName = "settings")
data class Settings(
    val darkMode: Boolean = false,
    val fontScale: Float = 1.0f,
    val language: String = "en",
    val notificationsEnabled: Boolean = true,
    val biometricEnabled: Boolean = false
)
