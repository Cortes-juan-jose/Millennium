package com.app.millennium.domain.use_case.product_db

import com.app.millennium.data.model.Product
import com.app.millennium.data.repository.RepositoryDataSource
import com.google.android.gms.tasks.Task

/**
 * Caso de uso para guardar productos en la db Collection products
 */
class SaveProductUseCase {

    private val repository = RepositoryDataSource.remote.firebase.firestore.products

    suspend operator fun invoke(product: Product): Task<Void> =
        repository.save(product)

}