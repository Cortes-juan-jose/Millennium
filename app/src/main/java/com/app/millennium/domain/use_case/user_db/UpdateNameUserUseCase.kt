package com.app.millennium.domain.use_case.user_db

import com.app.millennium.data.model.User
import com.app.millennium.data.repository.RepositoryDataSource

class UpdateNameUserUseCase {
    private val repository = RepositoryDataSource.remote.firebase.firestore.users

    suspend operator fun invoke(id: String, newValue: String) =
        repository.updateName(id, newValue)
}