package com.app.millennium.domain.use_case.token_db

import com.app.millennium.data.repository.RepositoryDataSource
import com.google.android.gms.tasks.Task

class GetTokenToDeviceUseCase {
    private val repository = RepositoryDataSource.remote.firebase.firestore.tokens

    suspend operator fun invoke(): Task<String> =
        repository.getTokenToDevice()
}