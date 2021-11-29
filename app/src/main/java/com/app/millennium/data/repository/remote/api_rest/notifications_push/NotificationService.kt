package com.app.millennium.data.repository.remote.api_rest.notifications_push

import com.app.millennium.core.api_rest.RetrofitHelper
import com.app.millennium.data.model.FCMBody
import com.app.millennium.data.model.FCMResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.Body

class NotificationService {

    //Obtenemos el objeto que construye la api
    private val api = RetrofitHelper.getRetrofit()

    suspend fun send(@Body body: FCMBody): FCMResponse {

        return withContext(Dispatchers.IO){
            val response = api.create(NotificationApiClient::class.java).send(body)
            response
        }
    }
}