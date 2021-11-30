package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.messages

import com.app.millennium.data.model.Message
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query

interface Messages {

    suspend fun create(msg: Message) : Task<Void>
    suspend fun getAllByChat(idChat: String) : Query
}