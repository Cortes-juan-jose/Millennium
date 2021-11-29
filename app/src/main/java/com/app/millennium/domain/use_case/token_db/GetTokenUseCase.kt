package com.app.millennium.domain.use_case.token_db

import com.app.millennium.data.repository.RepositoryDataSource
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

class GetTokenUseCase {
    private val repository = RepositoryDataSource.remote.firebase.firestore.tokens

    suspend operator fun invoke(idUser: String) : Task<DocumentSnapshot> =
        repository.get(idUser)
}