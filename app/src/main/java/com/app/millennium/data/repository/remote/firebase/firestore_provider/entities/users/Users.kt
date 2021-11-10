package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.users

import com.app.millennium.data.model.User
import com.google.android.gms.tasks.Task

interface Users {

    suspend fun save(user: User) : Task<Void>?
}