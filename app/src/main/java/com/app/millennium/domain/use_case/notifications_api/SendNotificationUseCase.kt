package com.app.millennium.domain.use_case.notifications_api

import com.app.millennium.data.model.FCMBody
import com.app.millennium.data.model.FCMResponse
import com.app.millennium.data.repository.RepositoryDataSource
import retrofit2.http.Body

class SendNotificationUseCase {
    private val repository = RepositoryDataSource.remote.apiRest.notifications

    suspend operator fun invoke(@Body body:FCMBody): FCMResponse =
        repository.send(body)
}