package com.app.millennium.domain.use_case.user_auth

import com.app.millennium.data.repository.remote.user_auth_reporitory.UserAuthRepositoryImpl
import com.google.firebase.auth.FirebaseUser

/**
 * Caso de uso para obtener el inicio de sesion del usuario
 */
class GetCurrentSessionUseCase {

    /**
     * Obtener el repositoprio user auth para verficiar
     * con el metodo getCurrentSession si se ha iniciado
     * sesion
     */
    private val userAuthRepositoryImpl = UserAuthRepositoryImpl()

    /**
     * Metodo que devuelve el usuario que se ha iniciado o tiene una
     * sesion abierta en la aplicacion
     */
    suspend operator fun invoke() : FirebaseUser? =
        userAuthRepositoryImpl.getCurrentSession()
}