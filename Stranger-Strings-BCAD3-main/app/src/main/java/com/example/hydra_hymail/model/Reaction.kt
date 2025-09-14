package com.example.hydra_hymail.model

data class Reaction(
    val userId: String = "",
    val type: ReactionType = ReactionType.HELPFUL,
    val timestamp: Long = System.currentTimeMillis()
)

enum class ReactionType {
    HELPFUL,
    LOVE,
    WOW,
    COMMENT
}
