package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.opinions

import com.app.millennium.data.model.Opinion
import com.google.android.gms.tasks.Task

interface Opinions {

    suspend fun save(opinion: Opinion) : Task<Void>
}