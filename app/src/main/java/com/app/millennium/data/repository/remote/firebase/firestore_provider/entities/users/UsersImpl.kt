package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.users

import com.app.millennium.core.firebase.base.FirebaseProviderImpl
import com.app.millennium.data.model.User
import com.google.android.gms.tasks.Task

class UsersImpl: Users {

    private val db = FirebaseProviderImpl().getUsersCollection()

    /**
     * Meetodo para añadir un usuario a la db
     */
    override suspend fun save(user: User): Task<Void>? =
        user.id?.let { db.document(it).set(user) }
}