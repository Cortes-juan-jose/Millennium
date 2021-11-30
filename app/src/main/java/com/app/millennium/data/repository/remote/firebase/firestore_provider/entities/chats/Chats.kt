package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.chats

import com.app.millennium.data.model.Chat
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

interface Chats {

    suspend fun create(chat: Chat) : Task<Void>
    suspend fun getChatByUserToSessionByUserToChat(idUserToSession: String, idUserToChat: String): Task<QuerySnapshot>
    suspend fun getAllByUser(idUserToSession: String) : Query
}