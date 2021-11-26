package com.app.millennium.domain.use_case.opinion_db

import com.app.millennium.data.model.Opinion
import com.app.millennium.data.repository.RepositoryDataSource
import com.google.android.gms.tasks.Task

class SaveOpinionUseCase {

    private val repository = RepositoryDataSource.remote.firebase.firestore.opinions

    suspend operator fun invoke(opinion: Opinion): Task<Void> =
        repository.save(opinion)
}