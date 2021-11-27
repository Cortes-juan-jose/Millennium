package com.app.millennium.domain.use_case.opinion_db

import com.app.millennium.data.repository.RepositoryDataSource
import com.google.firebase.firestore.Query

class GetOpinionsReceivedByUserUseCase {
    private val repository = RepositoryDataSource.remote.firebase.firestore.opinions

    suspend operator fun invoke(idUser: String): Query =
        repository.getReceivedByUser(idUser)
}