package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.likes

import com.app.millennium.core.firebase.FirebaseProvider
import com.app.millennium.data.model.Like
import com.google.android.gms.tasks.Task

class LikesImpl : Likes {

    private val db = FirebaseProvider.likesCollection

    override suspend fun save(like: Like): Task<Void> =
        db.document().set(like)

}