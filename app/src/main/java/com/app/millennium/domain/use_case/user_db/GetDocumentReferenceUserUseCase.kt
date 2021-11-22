package com.app.millennium.domain.use_case.user_db

import com.app.millennium.data.repository.RepositoryDataSource
import com.google.firebase.firestore.DocumentReference

class GetDocumentReferenceUserUseCase {
    private val repository = RepositoryDataSource.remote.firebase.firestore.users

    suspend operator fun invoke(id: String): DocumentReference =
        repository.getDataRealTime(id)
}