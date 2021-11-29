package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.tokens

import com.app.millennium.core.firebase.FirebaseProvider
import com.app.millennium.data.model.Token
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.messaging.FirebaseMessaging

class TokensImpl : Tokens {

    private val db = FirebaseProvider.tokensCollection

    override suspend fun getTokenToDevice(): Task<String> =
        FirebaseMessaging.getInstance().token

    override suspend fun create(idUser: String, tokenToDevice: String): Task<Void>? {

        if (idUser.isEmpty())
            return null
        return db.document(idUser).set(Token(tokenToDevice))
    }

    override suspend fun get(idUser: String): Task<DocumentSnapshot> =
        db.document(idUser).get()
}