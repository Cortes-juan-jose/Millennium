package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.products

import com.app.millennium.core.common.Constant
import com.app.millennium.core.firebase.FirebaseProvider
import com.app.millennium.data.model.Product
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class ProductsImpl : Products {

    private val db = FirebaseProvider.productsCollection

    /**
     * Meetodo para añadir un producto a la db
     */
    override suspend fun save(product: Product): Task<Void> =
        db.document(product.id).set(product)

    /**
     * Metodo para obtener todos los productos
     */
    override suspend fun getAll(): Task<QuerySnapshot> =
        db.orderBy(Constant.PROP_TIMESTAMP_PRODUCT, Query.Direction.DESCENDING).get()

    /**
     * Metodo para obtener todos los productos de un usuario en tiempo real
     */
    override suspend fun getAllByUser(idUser: String): Query =
        db.whereEqualTo(Constant.PROP_ID_USER_PRODUCT, idUser)
            .orderBy(Constant.PROP_TIMESTAMP_PRODUCT, Query.Direction.DESCENDING)

    /**
     * Metodo que elimina un producto
     */
    override suspend fun delete(id: String): Task<Void> =
        db.document(id).delete()
}