package com.app.millennium.domain.use_case.likes_db

import com.app.millennium.data.model.Like
import com.app.millennium.data.repository.RepositoryDataSource
import com.google.android.gms.tasks.Task

class DeleteLikeUseCase {

    private val repository = RepositoryDataSource.remote.firebase.firestore.likes

    suspend operator fun invoke(id: String) : Task<Void> =
        repository.delete(id)
}