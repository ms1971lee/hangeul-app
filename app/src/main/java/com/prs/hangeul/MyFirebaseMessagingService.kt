package com.prs.hangeul

// Firebase disabled - 전체 파일 비활성화
/*
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "FCM Service"
        private const val CHANNEL_ID = "vue_app_channel"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "New FCM Token: $token")

        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        prefs.edit().putString("fcm_token", token).apply()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "From: ${remoteMessage.from}")

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            handleDataPayload(remoteMessage.data)
        }

        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            sendNotification(it.title ?: "알림", it.body ?: "")
        }
    }

    private fun handleDataPayload(data: Map<String, String>) {
        val title = data["title"] ?: "새 알림"
        val message = data["message"] ?: ""
        sendNotification(title, message)
    }

    private fun sendNotification(title: String, messageBody: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val channelId = createNotificationChannel()
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(messageBody))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }

    private fun createNotificationChannel(): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Vue App Notifications"
            val channelDescription = "Vue 앱 알림"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(CHANNEL_ID, channelName, importance).apply {
                description = channelDescription
                enableLights(true)
                enableVibration(true)
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
        return CHANNEL_ID
    }
}
*/
