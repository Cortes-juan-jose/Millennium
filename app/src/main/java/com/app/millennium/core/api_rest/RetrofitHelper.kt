package com.app.millennium.core.api_rest

import com.app.millennium.core.common.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Objeto que devuelve el retrofit construido con la url de la api
 * de notificaciones
 */
object RetrofitHelper {

    fun getRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(Constant.URL_API_NOTIFICATION)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}