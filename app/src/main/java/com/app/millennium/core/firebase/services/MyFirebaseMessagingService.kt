package com.app.millennium.core.firebase.services

import com.app.millennium.core.channels.NotificationHelper
import com.app.millennium.core.common.Constant
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val data: Map<String, String> = remoteMessage.data
        val title = data[Constant.TITLE_NOTIFICATION]
        val body = data[Constant.BODY_NOTIFICATION]

        title?.let { showNotification(it, body!!) }
    }

    private fun showNotification(title: String, body: String){
        val notificationHelper = NotificationHelper(baseContext)
        val notification = notificationHelper.getNotification(title, body)
        notificationHelper.getManager().notify(1, notification.build())
    }

}