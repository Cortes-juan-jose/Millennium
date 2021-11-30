package com.app.millennium.domain.use_case.chat_db

import com.app.millennium.data.repository.RepositoryDataSource
import com.google.firebase.firestore.Query

class GetAllChatsByUserUseCase {
    private val repository = RepositoryDataSource.remote.firebase.firestore.chats

    suspend operator fun invoke(idUserToSession: String) : Query =
        repository.getAllByUser(idUserToSession)
}