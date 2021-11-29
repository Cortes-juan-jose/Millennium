package com.app.millennium.core.firebase.providers.firestore

import com.app.millennium.core.firebase.providers.firestore.entities.*

/**
 * Objeto para obtener todas las colecciones de datos de firestore
 */
object FirestoreProvider {
    val users = Users.db
    val products = Products.db
    val likes = Likes.db
    val opinions = Opinions.db
}