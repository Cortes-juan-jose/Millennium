package com.app.millennium.domain.use_case.user_auth

import com.app.millennium.data.repository.RepositoryDataSource

class SignOutUseCase {
    private val repository = RepositoryDataSource.remote.firebase.auth

    suspend operator fun invoke() =
        repository.signOut()
}