package com.app.millennium.data.repository.remote.api_rest.notifications_push

import com.app.millennium.core.common.Constant
import com.app.millennium.data.model.FCMBody
import com.app.millennium.data.model.FCMResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationApiClient {

    /*
     * Metodo post para enviar una notificacion
     * Y devuelve un callback con el response
     */
    @Headers(
        Constant.CONTENT_TYPE_API_NOTIFICATION,
        Constant.AUTHORIZATION_KEY_API_NOTIFICATION
    )
    @POST("fcm/send")
     suspend fun send(@Body body: FCMBody): FCMResponse
}