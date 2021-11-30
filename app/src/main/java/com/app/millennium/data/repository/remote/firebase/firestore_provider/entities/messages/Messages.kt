package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.messages

import com.app.millennium.data.model.Message
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

interface Messages {

    suspend fun create(msg: Message) : Task<Void>
    suspend fun getAllByChat(idChat: String) : Query
    suspend fun getAllByChatBySender(idChat: String, idSender: String): Task<QuerySnapshot>
    suspend fun updateViewed(idMessage: String, state: Boolean) : Task<Void>
}