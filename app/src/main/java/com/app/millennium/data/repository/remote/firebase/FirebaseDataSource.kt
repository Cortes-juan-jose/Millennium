package com.app.millennium.data.repository.remote.firebase

import com.app.millennium.data.repository.remote.firebase.auth_provider.AuthProviderImpl
import com.app.millennium.data.repository.remote.firebase.firestore_provider.FirestoreDataSource

/**
 * Objeto para obtener los provider de firebase
 */
object FirebaseDataSource {

    val auth = AuthProviderImpl()
    val firestore = FirestoreDataSource
}