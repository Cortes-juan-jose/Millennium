package com.app.millennium.core.channels

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.app.millennium.R
import com.app.millennium.core.common.Constant
import com.app.millennium.core.common.isNull

class NotificationHelper(context: Context) : ContextWrapper(context) {

    private val channelId = Constant.PACKAGE_PROJECT
    private val channelName = Constant.NAME_APP

    private val notificationManager = (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)

    /**
     * Metodo init que crea el canal de notificaciones si la version de android
     * es igual o superio a la de oreo
     */
    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannel()
        }
    }

    /**
     * Metodo que crea el canal de notificaciones
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(){
        val notificationChannel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationChannel.enableLights(true)
        notificationChannel.enableVibration(true)
        notificationChannel.lightColor = Color.GRAY
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        notificationManager.createNotificationChannel(notificationChannel)
    }

    /**
     * Metodo que devuelve el manager de la notificacion
     */
    fun getManager() : NotificationManager{

        return notificationManager
    }

    /**
     * Metodo que devuelve la notificacion con toda la configuracion establecida
     */
    fun getNotification(title: String, body: String): NotificationCompat.Builder {
        return NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setColor(Color.GRAY)
            .setSmallIcon(R.mipmap.ic_app)
            .setStyle(
                NotificationCompat
                    .BigTextStyle()
                    .bigText(title)
                    .setBigContentTitle(body)
            )
    }
}