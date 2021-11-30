package com.app.millennium.domain.use_case.messages_db

import com.app.millennium.data.repository.RepositoryDataSource
import com.google.android.gms.tasks.Task

class UpdateMessageViewedUseCase {

    private val repository = RepositoryDataSource.remote.firebase.firestore.messages

    suspend operator fun invoke(idMessage: String, state: Boolean) : Task<Void> =
        repository.updateViewed(idMessage, state)
}