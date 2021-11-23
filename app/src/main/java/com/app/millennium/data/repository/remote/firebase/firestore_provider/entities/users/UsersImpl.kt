package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.users

import com.app.millennium.core.common.Constant
import com.app.millennium.core.firebase.FirebaseProvider
import com.app.millennium.data.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

/**
 * Operaciones sobre la coleccion Users
 */
class UsersImpl: Users {

    private val db = FirebaseProvider.usersCollection

    /**
     * Meetodo para añadir un usuario a la db
     */
    override suspend fun save(user: User): Task<Void> =
        user.id?.let { db.document(it).set(user) } as Task<Void>

    override suspend fun get(id: String): Task<DocumentSnapshot> =
        db.document(id).get()

    override suspend fun getDataRealTime(id: String) =
        db.document(id)

    override suspend fun updateUploadedProducts(user: User): Task<Void> =
        db.document(user.id!!).update(Constant.PROP_UPLOADED_PRODUCTS_USER, user.uploadedProducts)

    override suspend fun updateImgCover(id: String, newValue: String): Task<Void> =
        db.document(id).update(Constant.PROP_IMG_COVER_USER, newValue)

    override suspend fun updateImgProfile(id: String, newValue: String): Task<Void> =
        db.document(id).update(Constant.PROP_IMG_PROFILE_USER, newValue)

    override suspend fun updateName(id: String, newValue: String): Task<Void> =
        db.document(id).update(Constant.PROP_USERNAME_USER, newValue)

    override suspend fun updatePhone(id: String, newValue: String): Task<Void> =
        db.document(id).update(Constant.PROP_PHONE_USER, newValue)
}