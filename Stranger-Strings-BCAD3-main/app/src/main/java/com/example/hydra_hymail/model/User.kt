package com.example.hydra_hymail.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.hydra_hymail.data.local.converters.BadgeListConverter
import com.example.hydra_hymail.data.local.converters.SettingsConverter

/**
 * User entity stored in Room for offline sync.
 * It matches the Firebase/remote model but has Room annotations.
 */
@Entity(tableName = "users")
@TypeConverters(BadgeListConverter::class, SettingsConverter::class)
data class User(
    @PrimaryKey val id: String, // Firebase UID or tenant ID
    val name: String = "",
    val email: String = "",
    val profilePictureUrl: String? = null,
    val role: UserRole = UserRole.CONSUMER,
    val badges: List<Badge> = emptyList(),
    val settings: Settings = Settings(),
    val joinedAt: Long = System.currentTimeMillis()
)

enum class UserRole {
    CONSUMER,
    TENANT,
    GUEST
}
