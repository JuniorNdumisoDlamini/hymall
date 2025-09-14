package com.example.hydra_hymail.utils

/**
 * Helper for mapping mood tags or quick reactions to emoji.
 * Useful when showing a mood picker or reaction icons in posts.
 */
object EmojiUtils {

    private val moodMap = mapOf(
        "happy" to "😊",
        "sad" to "😢",
        "angry" to "😡",
        "wow" to "😮",
        "love" to "❤️",
        "laugh" to "😂",
        "excited" to "🤩",
        "confused" to "😕",
        "bored" to "🥱",
        "tired" to "😴",
        "shy" to "🙈",
        "cool" to "😎",
        "thinking" to "🤔",
        "crying" to "😭",
        "blush" to "☺️",
        "sick" to "🤒",
        "mindblown" to "🤯",
        "celebrate" to "🥳",
        "hungry" to "🍔",
        "sleepy" to "🛌",
        "angrycry" to "😤",
        "relieved" to "😌",
        "kiss" to "😘",
        "nervous" to "😬",
        "clap" to "👏",
        "thumbs-up" to "👍",
        "thumbs-down" to "👎",
        "fire" to "🔥",
        "star" to "⭐",
        "ok" to "👌",
        "pray" to "🙏",
        "muscle" to "💪",
        "heartbroken" to "💔",
        "eyes" to "👀",
        "party" to "🎉"
    )

    fun getEmojiForMood(mood: String): String {
        return moodMap[mood.lowercase()] ?: "❓"
    }
}
