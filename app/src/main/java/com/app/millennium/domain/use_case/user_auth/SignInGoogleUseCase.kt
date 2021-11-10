package com.app.millennium.domain.use_case.user_auth

import com.app.millennium.data.repository.RepositoryDataSource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

/**
 * Caso de uso para iniciar sesion con las credenciales de google
 */
class SignInGoogleUseCase {

    private val repository = RepositoryDataSource.remote.firebase.auth

    suspend operator fun invoke(idToken: String) : Task<AuthResult> =
        repository
            .signInGoogle(idToken)

}
