package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.messages

import com.app.millennium.core.common.Constant
import com.app.millennium.core.firebase.FirebaseProvider
import com.app.millennium.data.model.Message
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import java.util.*

class MessagesImpl : Messages {

    private val db = FirebaseProvider.messagesCollection

    override suspend fun create(msg: Message): Task<Void> =
        db.document(msg.id).set(msg)

    override suspend fun getAllByChat(idChat: String): Query =
        db.whereEqualTo(Constant.PROP_ID_CHAT_MESSAGE, idChat)
            .orderBy(Constant.PROP_TIMESTAMP_MESSAGE, Query.Direction.ASCENDING)

    override suspend fun getAllByChatBySender(idChat: String, idSender: String): Task<QuerySnapshot> =
        db.whereEqualTo(Constant.PROP_ID_CHAT_MESSAGE, idChat)
            .whereEqualTo(Constant.PROP_ID_SENDER_MESSAGE, idSender)
            .whereEqualTo(Constant.PROP_VIEWED_MESSAGE, false)
            .get()

    override suspend fun updateViewed(idMessage: String, state: Boolean): Task<Void> {
        val map = mapOf<String, Boolean>(
            Constant.PROP_VIEWED_MESSAGE to state
        )
        return db.document(idMessage).update(map)
    }
}