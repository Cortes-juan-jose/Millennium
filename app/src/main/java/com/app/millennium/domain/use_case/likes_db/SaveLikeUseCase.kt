package com.app.millennium.domain.use_case.likes_db

import com.app.millennium.data.model.Like
import com.app.millennium.data.repository.RepositoryDataSource

class SaveLikeUseCase {

    private val repository = RepositoryDataSource.remote.firebase.firestore.likes

    suspend operator fun invoke(like: Like) =
        repository.save(like)

}