package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.chats

import com.app.millennium.data.model.Chat
import com.google.android.gms.tasks.Task

interface Chats {

    suspend fun createChatUserToSession(chat: Chat) : Task<Void>
    suspend fun createChatUserToChat(chat: Chat) : Task<Void>
}