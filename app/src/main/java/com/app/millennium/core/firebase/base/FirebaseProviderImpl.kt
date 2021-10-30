package com.app.millennium.core.firebase.base

import com.app.millennium.core.firebase.providers.AuthProvider
import com.google.firebase.auth.FirebaseAuth

class FirebaseProviderImpl : FirebaseProvider {

    /**
     * Metodo que devuelve el objeto AuthProvider
     */
    override fun getAuth(): FirebaseAuth = AuthProvider.getAuth()
}