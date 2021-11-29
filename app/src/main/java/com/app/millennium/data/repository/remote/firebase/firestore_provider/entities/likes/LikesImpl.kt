package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.likes

import com.app.millennium.core.common.Constant
import com.app.millennium.core.firebase.FirebaseProvider
import com.app.millennium.data.model.Like
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class LikesImpl : Likes {

    private val db = FirebaseProvider.likesCollection

    override suspend fun save(like: Like): Task<Void> =
        db.document(like.id).set(like)

    override suspend fun getLikeByProductByUserProductByUserSession(like: Like): Task<QuerySnapshot> =
        db.whereEqualTo(Constant.PROP_ID_USER_TO_SESSION_LIKE, like.idUserToSession)
            .whereEqualTo(Constant.PROP_ID_USER_TO_POST_PRODUCT_LIKE, like.idUserToPostProduct)
            .whereEqualTo(Constant.PROP_ID_PRODUCT_LIKE, like.idProduct).get()

    override suspend fun getAllByUser(idUser: String): Task<QuerySnapshot> =
        db.whereEqualTo(Constant.PROP_ID_USER_TO_SESSION_LIKE, idUser).get()

    override suspend fun delete(id: String): Task<Void> =
        db.document(id).delete()

    override suspend fun getAllByProduct(idProduct: String): Task<QuerySnapshot> =
        db.whereEqualTo(Constant.PROP_ID_PRODUCT_LIKE, idProduct).get()
}