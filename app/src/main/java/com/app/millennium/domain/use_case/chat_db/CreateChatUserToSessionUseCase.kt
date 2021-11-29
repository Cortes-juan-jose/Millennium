package com.app.millennium.domain.use_case.chat_db

import com.app.millennium.data.model.Chat
import com.app.millennium.data.repository.RepositoryDataSource
import com.google.android.gms.tasks.Task

class CreateChatUserToSessionUseCase {

    private val repository = RepositoryDataSource.remote.firebase.firestore.chats

    suspend operator fun invoke(chat: Chat): Task<Void> =
        repository.createChatUserToSession(chat)
}