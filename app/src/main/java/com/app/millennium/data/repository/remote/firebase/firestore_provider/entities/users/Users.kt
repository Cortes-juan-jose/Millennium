package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.users

import com.app.millennium.data.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

interface Users {

    suspend fun save(user: User) : Task<Void>?
    suspend fun get(id:String) : Task<DocumentSnapshot>
}