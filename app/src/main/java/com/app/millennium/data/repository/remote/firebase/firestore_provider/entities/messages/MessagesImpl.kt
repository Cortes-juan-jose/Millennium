package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.messages

import com.app.millennium.core.firebase.FirebaseProvider
import com.app.millennium.data.model.Message
import com.google.android.gms.tasks.Task

class MessagesImpl : Messages {

    private val db = FirebaseProvider.messagesCollection

    override suspend fun create(msg: Message): Task<Void> =
        db.document(msg.id).set(msg)
}