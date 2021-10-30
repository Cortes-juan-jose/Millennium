package com.app.millennium.domain.use_case.user_auth

import com.app.millennium.data.repository.remote.user_reporitory.UserAuthRepositoryImpl
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class SignInEmailAndPasswordUseCase {

    private val userAuthRepositoryImpl = UserAuthRepositoryImpl()

    suspend operator fun invoke(
        email: String,
        password: String
    ) : Task<AuthResult> {
        return userAuthRepositoryImpl.signInWithEmailAndPassword(email, password)
    }
}