package com.app.millennium.data.repository.remote.firebase.firestore_provider

import com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.users.UsersImpl

/**
 * Objeto para obtener la coleccion de los usuarios y realizar operaciones crud
 */
object FirestoreDataSource {
    val users = UsersImpl()
}