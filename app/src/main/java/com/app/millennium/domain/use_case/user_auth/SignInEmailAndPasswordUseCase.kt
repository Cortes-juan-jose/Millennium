package com.app.millennium.domain.use_case.user_auth

import com.app.millennium.data.repository.RepositoryDataSource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

/**
 * Caso de uso para iniciar sesion en firebase auth
 * con email y contraseña
 */
class SignInEmailAndPasswordUseCase {

    private val repository = RepositoryDataSource.remote.firebase.auth

    suspend operator fun invoke(
        email: String,
        password: String
    ) : Task<AuthResult> =
        repository
            .signInWithEmailAndPassword(email, password)
}