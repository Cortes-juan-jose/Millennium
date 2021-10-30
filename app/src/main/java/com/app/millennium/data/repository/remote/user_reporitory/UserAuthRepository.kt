package com.app.millennium.data.repository.remote.user_reporitory

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface UserAuthRepository {
    suspend fun signInWithEmailAndPassword (email: String, password: String) : Task<AuthResult>
}