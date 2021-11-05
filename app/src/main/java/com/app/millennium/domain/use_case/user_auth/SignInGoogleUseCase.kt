package com.app.millennium.domain.use_case.user_auth

import com.app.millennium.data.repository.remote.user_auth_reporitory.UserAuthRepositoryImpl
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

/**
 * Caso de uso para iniciar sesion con las credenciales de google
 */
class SignInGoogleUseCase {

    private val userAuthRepositoryImpl = UserAuthRepositoryImpl()

    suspend operator fun invoke(idToken: String) : Task<AuthResult> =
        userAuthRepositoryImpl.signInGoogle(idToken)

}
