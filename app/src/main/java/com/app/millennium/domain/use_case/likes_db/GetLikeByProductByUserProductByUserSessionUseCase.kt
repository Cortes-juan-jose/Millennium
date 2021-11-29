package com.app.millennium.domain.use_case.likes_db

import com.app.millennium.data.model.Like
import com.app.millennium.data.repository.RepositoryDataSource
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

class GetLikeByProductByUserProductByUserSessionUseCase {
    private val repository = RepositoryDataSource.remote.firebase.firestore.likes

    suspend operator fun invoke(like: Like) : Task<QuerySnapshot> =
        repository.getLikeByProductByUserProductByUserSession(like)
}