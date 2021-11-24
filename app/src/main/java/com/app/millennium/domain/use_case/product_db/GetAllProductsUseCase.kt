package com.app.millennium.domain.use_case.product_db

import com.app.millennium.data.repository.RepositoryDataSource
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

/**
 * Caso de uso que devuelve toda la lista de productos
 */
class GetAllProductsUseCase {

    private val repository = RepositoryDataSource.remote.firebase.firestore.products

    suspend operator fun invoke(): Task<QuerySnapshot> =
        repository.getAll()
}