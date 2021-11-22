package com.app.millennium.core.utils

import android.content.Context
import android.content.res.Configuration

object ConfigThemeApp {

    fun isThemeLight(context: Context): Boolean {
        return when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {false}
            Configuration.UI_MODE_NIGHT_NO -> {true}
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {false}
            else -> {false}
        }
    }
}