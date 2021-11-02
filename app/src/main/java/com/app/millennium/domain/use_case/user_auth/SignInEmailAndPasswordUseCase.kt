package com.app.millennium.domain.use_case.user_auth

import com.app.millennium.data.repository.remote.user_auth_reporitory.UserAuthRepositoryImpl
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

/**
 * Caso de uso para iniciar sesion en firebase auth
 */
class SignInEmailAndPasswordUseCase {

    /**
     * Obtener el repositoprio user auth para iniciar sesion
     * en firebase auth
     */
    private val userAuthRepositoryImpl = UserAuthRepositoryImpl()

    /**
     * Metodo que obtiene el email y contraseña e inicia sesión
     * con esta informacion y devuelve la tarea desempeñada que
     * es la de iniciar sesion en firebase auth
     */
    suspend operator fun invoke(
        email: String,
        password: String
    ) : Task<AuthResult>? =
        userAuthRepositoryImpl.signInWithEmailAndPassword(email, password)
}