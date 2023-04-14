package com.example.send_notifications_with_firebase

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val data = mapNotificationData(message.data)
        val name = data.name
        val message = data.message
        showLocalNotification(
            this@MyFirebaseMessagingService,
            name,
            message
        )
    }

    override fun onMessageSent(msgId: String) {
        super.onMessageSent(msgId)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

}
