package com.app.millennium.domain.use_case.user_auth

import com.app.millennium.data.repository.remote.user_auth_reporitory.UserAuthRepositoryImpl
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

/**
 * Caso de uso para crear una cuenta en firebase
 */
class CreateAccountUseCase {
    private val userAuthRepositoryImpl = UserAuthRepositoryImpl()

    suspend operator fun invoke(email: String, password: String) : Task<AuthResult> =
        userAuthRepositoryImpl.createAccount(email, password)
}