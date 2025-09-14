package com.example.hydra_hymail.services

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.hydra_hymail.R

object NotificationHandler {
    private const val CHANNEL_ID = "hymall_notifications"

    fun showNotification(context: Context, title: String, message: String) {
        createNotificationChannel(context)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                notify(System.currentTimeMillis().toInt(), builder.build())
            } else {
                // Permission not granted — could request permission here
            }
        }
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "HyMall Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "General notifications for HyMall"
            }

            val manager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}
