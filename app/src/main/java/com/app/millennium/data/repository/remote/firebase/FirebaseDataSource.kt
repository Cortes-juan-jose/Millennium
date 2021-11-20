package com.app.millennium.data.repository.remote.firebase

import com.app.millennium.data.repository.remote.firebase.auth_provider.AuthProviderImpl
import com.app.millennium.data.repository.remote.firebase.firestore_provider.FirestoreDataSource
import com.app.millennium.data.repository.remote.firebase.storage_provider.StorageDirectories

/**
 * Objeto para obtener los provider de firebase
 */
object FirebaseDataSource {

    val auth = AuthProviderImpl()
    val firestore = FirestoreDataSource
    val storage = StorageDirectories
}