package com.app.millennium.domain.use_case.chat_db

import com.app.millennium.data.repository.RepositoryDataSource
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

class GetChatByUserToSessionByUserToChatUseCase {

    private val repository = RepositoryDataSource.remote.firebase.firestore.chats

    suspend operator fun invoke(idUserToSession: String, idUserToChat: String) : Task<QuerySnapshot> =
        repository.getChatByUserToSessionByUserToChat(idUserToSession, idUserToChat)
}