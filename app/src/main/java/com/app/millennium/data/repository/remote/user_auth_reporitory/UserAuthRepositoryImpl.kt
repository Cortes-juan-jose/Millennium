package com.app.millennium.data.repository.remote.user_auth_reporitory

import com.app.millennium.core.firebase.base.FirebaseProviderImpl
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserAuthRepositoryImpl : UserAuthRepository {

    /**
     * Se obtiene el objeto auth
     */
    private val auth: FirebaseAuth = FirebaseProviderImpl().getAuth()

    /**
     * Metodo para iniciar sesion
     */
    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Task<AuthResult> =
        //Este metodo es el de iniciar sesion con firebase (que
        //se llama igual que este mismo pero no del mismo paquete)
        auth.signInWithEmailAndPassword(email, password)

    /**
     * Metodo para obtener el usuario que ha inicado sesión
     */
    override suspend fun getCurrentSession(): FirebaseUser =
        //Esto devuelve la sesion iniciada si no es nula
        //si es nula devuelve un null
        auth.currentUser!!
}