package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.products

import com.app.millennium.core.firebase.FirebaseProvider
import com.app.millennium.data.model.Product
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

class ProductsImpl : Products {

    private val db = FirebaseProvider.productsCollection

    /**
     * Meetodo para añadir un usuario a la db
     */
    override suspend fun save(product: Product): Task<Void> =
        db.document().set(product)
}