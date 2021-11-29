package com.app.millennium.domain.use_case.product_db

import com.app.millennium.data.repository.RepositoryDataSource
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

class GetProductUseCase {
    private val repository = RepositoryDataSource.remote.firebase.firestore.products

    suspend operator fun invoke(id: String): Task<DocumentSnapshot> =
        repository.get(id)
}