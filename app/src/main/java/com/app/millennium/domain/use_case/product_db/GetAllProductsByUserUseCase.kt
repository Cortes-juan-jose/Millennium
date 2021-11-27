package com.app.millennium.domain.use_case.product_db

import com.app.millennium.data.repository.RepositoryDataSource
import com.google.firebase.firestore.Query

/**
 * Caso de uso que devuelve todos los productos de un usuario
 */
class GetAllProductsByUserUseCase {
    private val repository = RepositoryDataSource.remote.firebase.firestore.products

    suspend operator fun invoke(idUser: String): Query =
        repository.getAllByUser(idUser)
}