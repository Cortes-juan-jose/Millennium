package com.app.millennium.data.repository.remote.user_reporitory

import com.app.millennium.core.firebase.base.FirebaseProviderImpl
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class UserAuthRepositoryImpl : UserAuthRepository {

    private val auth: FirebaseAuth = FirebaseProviderImpl().getAuth()

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Task<AuthResult> = auth.signInWithEmailAndPassword(email, password)
}