package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.likes

import com.app.millennium.data.model.Like
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

interface Likes {

    suspend fun save(like: Like) : Task<Void>
    suspend fun getLikeByProductByUserProductByUserSession(like: Like): Task<QuerySnapshot>
    suspend fun getAllByUser(idUser: String): Task<QuerySnapshot>
    suspend fun delete(id: String): Task<Void>
}