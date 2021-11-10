package com.app.millennium.data.repository.remote.firebase.user_auth_reporitory

import com.app.millennium.core.firebase.base.FirebaseProviderImpl
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class UserAuthImpl : UserAuth {

    /**
     * Se obtiene el objeto auth
     */
    private val auth: FirebaseAuth = FirebaseProviderImpl().getAuth()

    override suspend fun createAccount(email: String, password: String): Task<AuthResult> =
        auth.createUserWithEmailAndPassword(email, password)

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
     * Metodo para iniciar sesion con las credenciales de google
     */
    override suspend fun signInGoogle(idToken: String): Task<AuthResult> {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return auth.signInWithCredential(credential)
    }

    /**
     * Metodo para obtener el usuario que ha inicado sesión
     */
    override suspend fun getCurrentSession(): FirebaseUser? =
        auth.currentUser
}