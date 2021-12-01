package com.app.millennium.domain.use_case.product_db

import com.app.millennium.data.repository.RepositoryDataSource
import com.google.firebase.firestore.Query

class GetAllProductsByCategoryUseCase {

    private val repository = RepositoryDataSource.remote.firebase.firestore.products

    suspend operator fun invoke(category: String) : Query =
        repository.getAllByCategory(category)
}