package com.example.hydra_hymail

/**
 * Represents an entry in the Tic Tac Toe leaderboard.
 *
 * @param userId The unique identifier for the player (used as display name for now).
 * @param points The total points the player has earned.
 */
data class LeaderboardEntry(
    val userId: String,
    val points: Int
)
