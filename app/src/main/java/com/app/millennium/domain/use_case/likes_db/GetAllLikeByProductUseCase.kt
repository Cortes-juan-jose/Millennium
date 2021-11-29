package com.app.millennium.domain.use_case.likes_db

import com.app.millennium.data.repository.RepositoryDataSource
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

class GetAllLikeByProductUseCase {

    private val repository = RepositoryDataSource.remote.firebase.firestore.likes

    suspend operator fun invoke(idProduct: String) : Task<QuerySnapshot> =
        repository.getAllByProduct(idProduct)
}