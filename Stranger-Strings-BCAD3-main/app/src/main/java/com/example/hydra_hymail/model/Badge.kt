package com.example.hydra_hymail.model

data class Badge(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val iconUrl: String = "",
    val earnedAt: Long = System.currentTimeMillis()
)
