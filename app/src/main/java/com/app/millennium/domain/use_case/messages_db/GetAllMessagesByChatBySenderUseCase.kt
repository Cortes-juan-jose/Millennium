package com.app.millennium.domain.use_case.messages_db

import com.app.millennium.data.repository.RepositoryDataSource
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

class GetAllMessagesByChatBySenderUseCase {

    private val repository = RepositoryDataSource.remote.firebase.firestore.messages

    suspend operator fun invoke(idChat: String, idSender: String): Task<QuerySnapshot> =
        repository.getAllByChatBySender(idChat, idSender)
}