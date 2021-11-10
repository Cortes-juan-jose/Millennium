package com.app.millennium.domain.use_case.user_auth

import com.app.millennium.data.repository.RepositoryDataSource

/**
 * Caso de uso para obtener el email del usuario que ha iniciado sesion
 */
class GetEmailUseCase {

    private val repository = RepositoryDataSource.remote.firebase.auth

    suspend operator fun invoke(): String? =
        repository.getEmail()
}