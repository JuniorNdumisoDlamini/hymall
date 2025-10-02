package com.example.hydra_hymail

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

/**
 * LeaderboardManager - lightweight SharedPreferences-backed manager.
 *
 * - Enforces per-user daily round limit (MAX_ROUNDS_PER_DAY).
 * - Stores points per user and a set of known users (leaderboard_users) so getLeaderboard() can list users.
 * - Uses Africa/Johannesburg timezone when calculating "today".
 *
 * This implementation avoids heavy APIs and works on older Android versions.
 */
object LeaderboardManager {
    private const val PREFS_NAME = "ttt_leaderboard_prefs"
    private const val KEY_POINTS_PREFIX = "points_"
    private const val KEY_ROUNDS_DATE_PREFIX = "rounds_date_"
    private const val KEY_ROUNDS_PREFIX = "rounds_"
    private const val USERS_KEY = "leaderboard_users"
    private const val MAX_ROUNDS_PER_DAY = 11

    // Use Africa/Johannesburg timezone for "today"
    private val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("Africa/Johannesburg")
    }

    private fun prefs(context: Context) = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private fun todayString(): String = dateFormat.format(Date())

    /** -------- Keys helpers -------- */
    private fun pointsKey(userId: String) = KEY_POINTS_PREFIX + userId
    private fun roundsDateKey(userId: String) = KEY_ROUNDS_DATE_PREFIX + userId
    private fun roundsCountKey(userId: String) = KEY_ROUNDS_PREFIX + userId

    /** -------- Points & Users set -------- */

    private fun addUserToSetIfNeeded(context: Context, userId: String) {
        val p = prefs(context)
        val existing = p.getStringSet(USERS_KEY, null)?.toMutableSet() ?: mutableSetOf()
        if (!existing.contains(userId)) {
            existing.add(userId)
            // putStringSet requires providing a new Set instance (not a view from prefs)
            p.edit().putStringSet(USERS_KEY, existing).apply()
        }
    }

    /**
     * Add deltaPoints to a user's points (delta may be negative).
     * Also ensures the user is present in the known-users set.
     */
    fun addPoints(context: Context, userId: String, deltaPoints: Int) {
        val p = prefs(context)
        val key = pointsKey(userId)
        val current = p.getInt(key, 0)
        p.edit().putInt(key, current + deltaPoints).apply()
        addUserToSetIfNeeded(context, userId)
    }

    /**
     * Set absolute points for a user.
     * Also ensures the user is present in the known-users set.
     */
    fun setPoints(context: Context, userId: String, points: Int) {
        val p = prefs(context)
        p.edit().putInt(pointsKey(userId), points).apply()
        addUserToSetIfNeeded(context, userId)
    }

    /**
     * Get points for a user (0 if none).
     */
    fun getPoints(context: Context, userId: String): Int {
        val p = prefs(context)
        return p.getInt(pointsKey(userId), 0)
    }

    /**
     * Returns leaderboard entries sorted by points descending.
     * Uses the stored users set to find all known users.
     */
    fun getLeaderboard(context: Context): List<LeaderboardEntry> {
        val p = prefs(context)
        val users = p.getStringSet(USERS_KEY, emptySet()) ?: emptySet()
        val list = users.map { uid ->
            LeaderboardEntry(uid, p.getInt(pointsKey(uid), 0))
        }.sortedByDescending { it.points }
        return list
    }

    /** -------- Rounds per day enforcement (MAX_ROUNDS_PER_DAY per day) -------- */

    /**
     * Returns true if the given user may start a new round now.
     * If there is no stored date or the stored date is not today, this resets the counter for the user to 0.
     */
    fun canPlayRound(context: Context, userId: String): Boolean {
        val p = prefs(context)
        val today = todayString()
        val dateKey = roundsDateKey(userId)
        val countKey = roundsCountKey(userId)

        val storedDate = p.getString(dateKey, null)
        if (storedDate == null || storedDate != today) {
            // Reset for new day
            p.edit()
                .putString(dateKey, today)
                .putInt(countKey, 0)
                .apply()
            return true
        }
        val rounds = p.getInt(countKey, 0)
        return rounds < MAX_ROUNDS_PER_DAY
    }

    /**
     * Records that the user has played one round today.
     * If day rolled over, it resets the date and starts at 1.
     * Call this after a finished round (win/lose/draw).
     */
    fun recordRoundPlayed(context: Context, userId: String) {
        val p = prefs(context)
        val today = todayString()
        val dateKey = roundsDateKey(userId)
        val countKey = roundsCountKey(userId)

        val storedDate = p.getString(dateKey, null)
        if (storedDate == null || storedDate != today) {
            // New day -> set count to 1
            p.edit()
                .putString(dateKey, today)
                .putInt(countKey, 1)
                .apply()
        } else {
            // Same day -> increment
            val current = p.getInt(countKey, 0)
            p.edit().putInt(countKey, current + 1).apply()
        }
    }

    /**
     * Returns how many rounds the user has left today (0..MAX_ROUNDS_PER_DAY).
     */
    fun getRoundsLeft(context: Context, userId: String): Int {
        val p = prefs(context)
        val today = todayString()
        val dateKey = roundsDateKey(userId)
        val countKey = roundsCountKey(userId)

        val storedDate = p.getString(dateKey, null)
        val used = if (storedDate == today) p.getInt(countKey, 0) else 0
        return (MAX_ROUNDS_PER_DAY - used).coerceAtLeast(0)
    }

    /** -------- Utilities for testing / maintenance -------- */

    /**
     * Remove one or more users from leaderboard storage (points + rounds keys + users set).
     * Use this once to remove leftover demo accounts (DemoUser1/DemoUser2) and then remove the call.
     */
    fun removeUsers(context: Context, userIds: List<String>) {
        val p = prefs(context)
        val users = p.getStringSet(USERS_KEY, emptySet())?.toMutableSet() ?: mutableSetOf()
        val editor = p.edit()
        for (uid in userIds) {
            editor.remove(pointsKey(uid))
            editor.remove(roundsDateKey(uid))
            editor.remove(roundsCountKey(uid))
            users.remove(uid)
        }
        editor.putStringSet(USERS_KEY, users)
        editor.apply()
    }

    /**
     * Resets the rounds counter for a user (useful for testing).
     */
    @Suppress("unused")
    fun resetRoundsForUser(context: Context, userId: String) {
        val p = prefs(context)
        val dateKey = roundsDateKey(userId)
        val countKey = roundsCountKey(userId)
        p.edit().remove(dateKey).remove(countKey).apply()
    }

    /**
     * Clear leaderboard users and points (use with caution).
     */
    @Suppress("unused")
    fun clearLeaderboard(context: Context) {
        val p = prefs(context)
        val users = p.getStringSet(USERS_KEY, emptySet()) ?: emptySet()
        val editor = p.edit()
        for (u in users) {
            editor.remove(pointsKey(u))
            editor.remove(roundsDateKey(u))
            editor.remove(roundsCountKey(u))
        }
        editor.remove(USERS_KEY)
        editor.apply()
    }
}
