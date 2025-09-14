package com.example.hydra_hymail.data.api



import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * FirebaseGamificationApi
 *
 * Simple wrapper to award badges, update points, and read leaderboard entries.
 * Collections used:
 *  - badges (badge definitions)
 *  - userBadges (documents per user)
 *  - leaderboard (documents with userId and points)
 *
 * Note: You might prefer a single 'users' doc storing points and badges. This class shows one sane separation.
 */
class FirebaseGamificationApi(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val badgesColl = firestore.collection("badges")
    private val userBadgesColl = firestore.collection("userBadges")
    private val leaderboardColl = firestore.collection("leaderboard")

    // Award a badge to a user (creates entry in userBadges)
    suspend fun awardBadge(userId: String, badgeId: String, metadata: Map<String, Any>) {
        val doc = hashMapOf(
            "userId" to userId,
            "badgeId" to badgeId
        ).also { it.putAll(metadata as Map<out String, out String>) }

        userBadgesColl.document("$userId-$badgeId").set(doc).await()
    }

    // Update leaderboard points (atomically via transaction recommended; simple set shown)
    suspend fun updatePoints(userId: String, points: Long) {
        leaderboardColl.document(userId).set(mapOf("userId" to userId, "points" to points)).await()
    }

    // Get leaderboard snapshot
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getTopLeaderboard(limit: Long = 50) : List<Map<String, Any>> {
        val snap = leaderboardColl.orderBy("points", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(limit)
            .get()
            .await()
        return snap.documents.mapNotNull { it.data?.plus("id" to it.id) }
    }
}
