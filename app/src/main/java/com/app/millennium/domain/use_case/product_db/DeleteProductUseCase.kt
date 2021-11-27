package com.app.millennium.domain.use_case.product_db

import com.app.millennium.data.repository.RepositoryDataSource
import com.google.android.gms.tasks.Task

class DeleteProductUseCase {

    private val repository = RepositoryDataSource.remote.firebase.firestore.products

    suspend operator fun invoke (id: String): Task<Void> =
        repository.delete(id)
}