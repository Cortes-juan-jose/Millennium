package com.app.millennium.core.firebase.base

import com.app.millennium.core.firebase.providers.auth.AuthProvider
import com.app.millennium.core.firebase.providers.firestore.FirestoreProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference

class FirebaseProviderImpl : FirebaseProvider {

    /**
     * Metodo que devuelve el objeto AuthProvider
     */
    override fun getAuth(): FirebaseAuth = AuthProvider.auth

    /**
     * Obtener el objeto de la coleccion de usuarios de firestore
     */
    override fun getUsersCollection(): CollectionReference =
        FirestoreProvider.users
}