package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.likes

import com.app.millennium.data.model.Like
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface Likes {

    suspend fun save(like: Like) : Task<Void>
    /*suspend fun get(
        id: String,
        idUserToSession: String,
        idUserToPostProduct: String
    ): Task<QuerySnapshot>*/
}