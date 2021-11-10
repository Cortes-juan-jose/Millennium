package com.app.millennium.core.firebase

import com.app.millennium.core.firebase.providers.auth.AuthProvider
import com.app.millennium.core.firebase.providers.firestore.FirestoreProvider

/**
 * Objeto para obtener las referencias en firebase
 */
object FirebaseProvider {
    val auth = AuthProvider.auth
    val usersCollection = FirestoreProvider.users
}