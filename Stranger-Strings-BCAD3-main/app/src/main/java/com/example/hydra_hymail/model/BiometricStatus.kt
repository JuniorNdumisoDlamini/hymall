package com.example.hydra_hymail.model

data class BiometricStatus(
    val userId: String = "",
    val enabled: Boolean = false,
    val lastUpdated: Long = System.currentTimeMillis()
)
