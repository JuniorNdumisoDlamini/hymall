package com.example.hydra_hymail.model

data class Comment(
    val id: String = "",
    val postId: String = "",
    val userId: String = "",
    val text: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
