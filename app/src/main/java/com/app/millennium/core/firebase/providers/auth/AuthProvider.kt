package com.app.millennium.core.firebase.providers.auth

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * Objeto firebase auth
 */
object AuthProvider {
    val auth = Firebase.auth
}