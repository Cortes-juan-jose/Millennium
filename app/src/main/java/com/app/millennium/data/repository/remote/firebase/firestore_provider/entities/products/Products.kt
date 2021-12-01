package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.products

import com.app.millennium.data.model.Product
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*

interface Products {
    suspend fun save(product: Product) : Task<Void>?
    suspend fun get(id: String) : Task<DocumentSnapshot>
    suspend fun getAll(): Task<QuerySnapshot>
    suspend fun getAllByUser(idUser: String): Query
    suspend fun getAllByCategory(category: String): Query
    suspend fun delete(id: String): Task<Void>
}