package com.app.millennium.domain.use_case.user_db

import com.app.millennium.data.repository.RepositoryDataSource

class UpdatePhoneUserUseCase {

    private val repository = RepositoryDataSource.remote.firebase.firestore.users

    suspend operator fun invoke(id: String, newValue: String) =
        repository.updatePhone(id, newValue)
}