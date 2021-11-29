package com.app.millennium.core.firebase

import com.app.millennium.core.firebase.providers.auth.AuthProvider
import com.app.millennium.core.firebase.providers.firestore.FirestoreProvider
import com.app.millennium.core.firebase.providers.storage.StorageProvider

/**
 * Objeto para obtener las referencias en firebase
 */
object FirebaseProvider {
    val auth = AuthProvider.auth

    val usersCollection = FirestoreProvider.users
    val productsCollection = FirestoreProvider.products
    val likesCollection = FirestoreProvider.likes
    val opinionsCollection = FirestoreProvider.opinions

    val storage_images = StorageProvider.storage_images
}