package com.example.send_notifications_with_firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.mo_chatting.chatapp.data.models.NotificationData
import com.mo_chatting.chatapp.data.models.PushNotification
import com.mo_chatting.chatapp.data.retrofit.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun showLocalNotification(
    context: Context,
    name: String,
    message: String,
) {

    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent: PendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE)
    } else {
        PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )
    }
    val CHANNEL_ID = "Main Chanel"
    val notificationBuilder: NotificationCompat.Builder =
        NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(name)
            .setContentText(message)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val name: CharSequence = "Main Notification Channel"
    val importance = NotificationManager.IMPORTANCE_HIGH
    val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
    notificationManager.createNotificationChannel(mChannel)
    notificationManager.notify(
        123,
        notificationBuilder.build()
    )
}

fun sendFireBaseNotification(notification: PushNotification) =
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance
                .apiService
                .sendNotification(notification)

            if (response.isSuccessful) {
            } else {
            }
        } catch (e: Exception) {
        }
    }

fun mapNotificationData(map: Map<String, String>): NotificationData {
    val name = map["name"] ?: ""
    val message = map["message"] ?: ""
    return NotificationData(name, message)
}
