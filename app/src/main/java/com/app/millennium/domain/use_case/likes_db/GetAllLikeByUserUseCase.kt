package com.app.millennium.domain.use_case.likes_db

import com.app.millennium.data.model.Like
import com.app.millennium.data.repository.RepositoryDataSource
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

class GetAllLikeByUserUseCase {

    private val repository = RepositoryDataSource.remote.firebase.firestore.likes

    suspend operator fun invoke(idUser: String): Task<QuerySnapshot> =
        repository.getAllByUser(idUser)

}