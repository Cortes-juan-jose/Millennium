package com.app.millennium.core.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.app.millennium.R
import com.app.millennium.core.utils.RelativeTime
import java.text.SimpleDateFormat
import java.util.*

/**
 * OBJETO QUE SIRVE PARA ESTABLECER UN TIEMPO SEGUN EL TIMESTAMP ESTABLECIDO
 */
object RelativeTime : Application() {

    private const val SECOND_MILLIS = 1000
    private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS = 24 * HOUR_MILLIS

    fun getTimeAgo(time: Long, ctx: Context?): String? {
        var time = time
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000
        }
        val now = System.currentTimeMillis()
        if (time > now || time <= 0) {
            return null
        }

        val diff = now - time
        return when {
            diff < MINUTE_MILLIS -> {
                //"Hace un momento"
                "${getString(R.string.msg_timestamp_hace)} ${getString(R.string.msg_timestamp_un_momento)}"
            }
            diff < 2 * MINUTE_MILLIS -> {
                //"Hace un minuto"
                "${getString(R.string.msg_timestamp_hace)} ${getString(R.string.msg_timestamp_un_minuto)}"
            }
            diff < 50 * MINUTE_MILLIS -> {
                //"Hace " + diff / MINUTE_MILLIS + " minutos"
                "${getString(R.string.msg_timestamp_hace)} " +
                        diff / MINUTE_MILLIS +
                        " ${getString(R.string.msg_timestamp_minutos)}"
            }
            diff < 90 * MINUTE_MILLIS -> {
                //"Hace una hora"
                "${getString(R.string.msg_timestamp_hace)} ${getString(R.string.msg_timestamp_una_hora)}"
            }
            diff < 24 * HOUR_MILLIS -> {
                //"Hace " + diff / HOUR_MILLIS + " horas"
                "${getString(R.string.msg_timestamp_hace)} " +
                        diff / HOUR_MILLIS +
                        " ${getString(R.string.msg_timestamp_horas)}"
            }
            diff < 48 * HOUR_MILLIS -> {
                //"Ayer"
                getString(R.string.msg_timestamp_ayer)
            }
            else -> {
                //"Hace " + diff / DAY_MILLIS + " dias"
                "${getString(R.string.msg_timestamp_hace)} " +
                        diff / DAY_MILLIS +
                        " ${getString(R.string.msg_timestamp_dias)}"
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun timeFormatAMPM(timestamp: Long): String {
        val formatter = SimpleDateFormat("hh:mm a")
        return formatter.format(Date(timestamp))
    }
}