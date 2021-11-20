package com.app.millennium.domain.use_case.user_db

import com.app.millennium.data.model.User
import com.app.millennium.data.repository.RepositoryDataSource
import com.google.android.gms.tasks.Task

/**
 * Caso de uso para guardar usuarios en la db Collection users
 */
class SaveUserUseCase {
    private val repository = RepositoryDataSource.remote.firebase.firestore.users

    suspend operator fun invoke(user: User): Task<Void> =
        repository.save(user)
}