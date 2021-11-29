package com.app.millennium.domain.use_case.token_db

import com.app.millennium.data.repository.RepositoryDataSource
import com.google.android.gms.tasks.Task

class CreateTokenUseCase {
    private val repository = RepositoryDataSource.remote.firebase.firestore.tokens

    suspend operator fun invoke(idUser: String, tokenToDevice: String): Task<Void>? =
        repository.create(idUser, tokenToDevice)
}