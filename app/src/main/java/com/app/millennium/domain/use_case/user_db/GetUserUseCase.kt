package com.app.millennium.domain.use_case.user_db

import com.app.millennium.data.repository.RepositoryDataSource
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

/**
 * Caso de uso para obtener un usuario por el ID
 */
class GetUserUseCase {
    private val repository = RepositoryDataSource.remote.firebase.firestore.users

    suspend operator fun invoke(id: String): Task<DocumentSnapshot> =
        repository.get(id)
}