package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.products

import com.app.millennium.data.model.Product
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

interface Products {
    suspend fun save(product: Product) : Task<Void>
    suspend fun getAll(): Task<QuerySnapshot>
    suspend fun getAllByUser(idUser: String): Query
}