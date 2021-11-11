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

    /**
     * Meetodo para añadir un usuario a la db
     */
    override suspend fun save(user: User): Task<Void>? =
        user.id?.let { db.document(it).set(user) }

    override suspend fun get(id: String): Task<DocumentSnapshot> =
        db.document(id).get()
}