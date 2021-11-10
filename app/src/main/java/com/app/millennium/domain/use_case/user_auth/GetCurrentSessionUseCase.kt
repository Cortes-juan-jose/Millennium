package com.app.millennium.domain.use_case.user_auth

import com.app.millennium.data.repository.RepositoryDataSource
import com.google.firebase.auth.FirebaseUser

/**
 * Caso de uso para obtener el inicio de sesion del usuario
 */
class GetCurrentSessionUseCase {

    private val repository = RepositoryDataSource.remote.firebase.auth

    suspend operator fun invoke() : FirebaseUser? =
        repository
            .getCurrentSession()
}