package com.app.millennium.data.repository.remote.firebase.auth_provider

import com.app.millennium.core.firebase.FirebaseProvider
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class AuthProviderImpl : AuthProvider {

    /**
     * Se obtiene el objeto auth
     */
    private val auth: FirebaseAuth = FirebaseProvider.auth

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

    /**
     * Metodo que devuelve el id del usuario que ha iniciado sesion
     */
    override suspend fun getId(): String? =
        auth.currentUser?.uid

    /**
     * Metodo que devuelve el email del usuario que ha iniciado sesion
     */
    override suspend fun getEmail(): String? =
        auth.currentUser?.email

    /**
     * Metodo que devuelve el displayName del usuario que ha iniciado sesion
     */
    override suspend fun getDisplayName(): String? =
        auth.currentUser?.displayName
}