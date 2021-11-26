package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.opinions

import com.app.millennium.core.common.Constant
import com.app.millennium.core.firebase.FirebaseProvider
import com.app.millennium.data.model.Opinion
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

class OpinionsImpl : Opinions {

    private val db = FirebaseProvider.opinionsCollection

    override suspend fun save(opinion: Opinion): Task<Void> =
        db.document().set(opinion)

    override suspend fun getMadeByUser(idUser: String): Task<QuerySnapshot> =
        db.whereEqualTo(Constant.PROP_ID_USER_CREATOR_OPINION, idUser).get()

    override suspend fun getReceivedByUser(idUser: String): Task<QuerySnapshot> =
        db.whereEqualTo(Constant.PROP_ID_USER_RECEPTOR_OPINION, idUser).get()
}