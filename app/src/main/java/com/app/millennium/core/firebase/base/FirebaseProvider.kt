package com.app.millennium.core.firebase.base

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference

interface FirebaseProvider {

    /**
     * Obtener el objeto de autenticacion de firebase
     */
    fun getAuth(): FirebaseAuth?

    /**
     * Obtener el objeto de la coleccion de usuarios de firestore
     */
    fun getUsersCollection(): CollectionReference?

}