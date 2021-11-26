package com.app.millennium.domain.use_case.opinion_db

import com.app.millennium.data.repository.RepositoryDataSource
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

class GetOpinionMadeByUserUseCase {
    private val repository = RepositoryDataSource.remote.firebase.firestore.opinions

    suspend operator fun invoke(idUser: String): Task<QuerySnapshot> =
        repository.getMadeByUser(idUser)
}