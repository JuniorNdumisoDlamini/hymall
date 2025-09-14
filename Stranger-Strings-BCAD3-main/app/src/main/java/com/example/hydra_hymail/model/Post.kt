package com.example.hydra_hymail.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "post")
data class Post(
    @PrimaryKey val id: String = "",
    val userId: String = "",
    val contentText: String = "",
    val mediaUrls: List<String> = emptyList(),
    val category: Category = Category.OTHER,
    val visibility: Visibility = Visibility.PUBLIC,
    val moodEmoji: String? = null,
    val hashtags: List<String> = emptyList(),
    val location: LocationData? = null,
    val reactions: List<Reaction> = emptyList(),
    val comments: List<Comment> = emptyList(),
    val createdAt: Long = System.currentTimeMillis()
)
