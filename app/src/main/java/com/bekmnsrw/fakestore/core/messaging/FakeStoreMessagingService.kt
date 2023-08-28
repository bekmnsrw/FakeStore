package com.bekmnsrw.fakestore.core.messaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.bekmnsrw.fakestore.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class FakeStoreMessagingService : FirebaseMessagingService() {

    companion object {
        const val CHANNEL_NAME = "FCM notification channel"
        const val NEW_TOKEN_TAG = "FCM_TAG"
    }

    private val random = Random

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let { message ->
            sendNotification(message)
        }
    }

    override fun onNewToken(token: String) {
        Log.d(NEW_TOKEN_TAG, "New token: $token")
    }

    private fun sendNotification(message: RemoteMessage.Notification) {
        val intent = Intent(this, FakeStoreMessagingService::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = this.getString(R.string.fcm_notification_channel_id)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(message.title)
            .setContentText(message.body)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                NotificationChannel(
                    channelId, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }

        manager.notify(random.nextInt(), notificationBuilder.build())
    }
}
