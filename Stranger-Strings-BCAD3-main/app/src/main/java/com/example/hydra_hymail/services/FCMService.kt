package com.example.hydra_hymail.services



import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.util.Log

/**
 * Handles Firebase Cloud Messaging push notifications.
 * This will be triggered whenever our backend or Firebase sends a notification to the device.
 */
class FCMService : FirebaseMessagingService() {

    // Called when a new token is generated for this device (used to send targeted notifications)
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCMService", "New token: $token")
        // TODO: Send token to backend so server knows this device can be notified
    }

    // Called when a new notification arrives
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("FCMService", "From: ${remoteMessage.from}")

        // If the notification has a body, we display it
        remoteMessage.notification?.let {
            NotificationHandler.showNotification(
                this,
                it.title ?: "HyMall",
                it.body ?: ""
            )
        }
    }
}
