package com.app.millennium.data.repository.remote.api_rest

import com.app.millennium.data.repository.remote.api_rest.notifications_push.NotificationService

object ApiRestDataSource {
    val notifications = NotificationService()
}