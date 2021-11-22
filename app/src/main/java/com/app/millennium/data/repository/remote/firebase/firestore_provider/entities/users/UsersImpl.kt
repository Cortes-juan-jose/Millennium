package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.users

import com.app.millennium.core.firebase.FirebaseProvider
import com.app.millennium.data.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

/**
 * Operaciones sobre la coleccion Users
 */
class UsersImpl: Users {

    private val db = FirebaseProvider.usersCollection

    private val UPLOADED_PRODUCTS = "uploadedProducts"

    /**
     * Meetodo para añadir un usuario a la db
     */
    override suspend fun save(user: User): Task<Void> =
        user.id?.let { db.document(it).set(user) } as Task<Void>

    override suspend fun get(id: String): Task<DocumentSnapshot> =
        db.document(id).get()

    override suspend fun getDataRealTime(id: String) =
        db.document(id)

    override suspend fun updateUploadedProducts(user: User): Task<Void> =
        db.document(user.id!!).update(UPLOADED_PRODUCTS, user.uploadedProducts)
}