package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.messages

import com.app.millennium.data.model.Message
import com.google.android.gms.tasks.Task

interface Messages {

    suspend fun create(msg: Message) : Task<Void>
}