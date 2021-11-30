package com.app.millennium.domain.use_case.messages_db

import com.app.millennium.data.model.Message
import com.app.millennium.data.repository.RepositoryDataSource
import com.google.android.gms.tasks.Task

class CreateMessageUseCase {
    private val repository = RepositoryDataSource.remote.firebase.firestore.messages

    suspend operator fun invoke(msg: Message) : Task<Void> =
        repository.create(msg)
}