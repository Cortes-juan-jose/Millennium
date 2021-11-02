package com.app.millennium.core.firebase.base

import com.google.firebase.auth.FirebaseAuth

interface FirebaseProvider {

    /**
     * Obtener el objeto de autenticacion de firebase
     */
    fun getAuth(): FirebaseAuth?

}