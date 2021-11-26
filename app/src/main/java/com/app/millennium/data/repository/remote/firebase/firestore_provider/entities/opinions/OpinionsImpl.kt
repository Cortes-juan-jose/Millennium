package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.opinions

import com.app.millennium.core.firebase.FirebaseProvider
import com.app.millennium.data.model.Opinion
import com.google.android.gms.tasks.Task

class OpinionsImpl : Opinions {

    private val db = FirebaseProvider.opinionsCollection

    override suspend fun save(opinion: Opinion): Task<Void> =
        db.document().set(opinion)
}