package com.app.millennium.core.firebase.providers

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object AuthProvider {

    fun getAuth (): FirebaseAuth = Firebase.auth

}