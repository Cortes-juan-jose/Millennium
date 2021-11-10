package com.app.millennium.domain.use_case.user_auth

import com.app.millennium.data.repository.RepositoryDataSource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

/**
 * Caso de uso para crear una cuenta en firebase
 */
class CreateAccountUseCase {
    private val repository = RepositoryDataSource.remote.firebase.auth

    suspend operator fun invoke(email: String, password: String) : Task<AuthResult> =
        repository
            .createAccount(email, password)
}