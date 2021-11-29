package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.tokens

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot

interface Tokens {
    suspend fun create(idUser: String, tokenToDevice: String): Task<Void>?
    suspend fun getTokenToDevice(): Task<String>
    suspend fun get(idUser: String): Task<DocumentSnapshot>
}