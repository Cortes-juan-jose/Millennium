package com.app.millennium.domain.use_case.user_auth

import com.app.millennium.data.repository.RepositoryDataSource

/**
 * Caso de uso para obtener el display name de un inicio de sesion
 */
class GetDisplayNameUseCase {
    private val repository = RepositoryDataSource.remote.firebase.auth

    suspend operator fun invoke(): String? =
        repository.getDisplayName()
}