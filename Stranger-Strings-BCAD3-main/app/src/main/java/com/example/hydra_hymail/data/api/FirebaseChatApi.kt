package com.example.hydra_hymail.data.api

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * FirebaseChatApi
 *
 * Responsibilities:
 * 1) Send user message to a Firebase Cloud Function ("chatbot") that proxies OpenAI.
 * 2) Persist chat messages in Firestore under chats/{sessionId}/messages.
 */
class FirebaseChatApi(
    private val functions: FirebaseFunctions = FirebaseFunctions.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    suspend fun callChatbotFunction(sessionId: String, message: String, language: String = "en"): String {
        val data = hashMapOf(
            "sessionId" to sessionId,
            "message" to message,
            "language" to language
        )
        val result = functions.getHttpsCallable("chatbot").call(data).await()
        val map = result.data as? Map<*, *>
        return map?.get("reply") as? String ?: "Sorry, no reply"
    }

    suspend fun persistChatMessage(sessionId: String, message: Map<String, Any>) {
        firestore.collection("chats")
            .document(sessionId)
            .collection("messages")
            .add(message)
            .await()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getChatMessages(sessionId: String): List<Map<String, Any>> {
        val snap = firestore.collection("chats")
            .document(sessionId)
            .collection("messages")
            .orderBy("timestamp")
            .get()
            .await()

        return snap.documents.mapNotNull { it.data?.plus("id" to it.id) }
    }
}
