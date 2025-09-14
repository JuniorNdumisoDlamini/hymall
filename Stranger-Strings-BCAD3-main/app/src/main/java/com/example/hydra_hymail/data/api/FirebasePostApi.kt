package com.example.hydra_hymail.data.api

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * FirebasePostApi
 *
 * Firestore wrapper for post CRUD operations.
 * Post documents are stored in "posts" collection.
 *
 * Each post document should contain at least:
 * - userId (author)
 * - content (text)
 * - mediaUrls (list)
 * - category, visibility, createdAt, updatedAt, location, etc.
 */
class FirebasePostApi(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    private val posts = firestore.collection("posts")

    suspend fun createPost(postData: Map<String, Any>): String {
        val docRef = posts.add(postData).await()
        return docRef.id
    }

    suspend fun getPost(postId: String): Map<String, Any>? {
        val snap = posts.document(postId).get().await()
        return if (snap.exists()) snap.data else null
    }

    suspend fun updatePost(postId: String, updates: Map<String, Any>) {
        posts.document(postId).update(updates).await()
    }

    suspend fun deletePost(postId: String) {
        posts.document(postId).delete().await()
    }

    // Basic paged query: returns list of post maps; you will want to map to model in repo
    suspend fun getRecentPosts(limit: Long = 50): List<Map<String, Any>> {
        val querySnap = posts.orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(limit)
            .get()
            .await()
        return querySnap.documents.mapNotNull { it.data?.plus("id" to it.id) }
    }
}
