package com.app.millennium.domain.use_case.messages_db

import com.app.millennium.data.repository.RepositoryDataSource
import com.google.firebase.firestore.Query

class GetAllMessagesByChatUseCase {

    private val repository = RepositoryDataSource.remote.firebase.firestore.messages

    suspend operator fun invoke(idChat: String): Query =
        repository.getAllByChat(idChat)
}