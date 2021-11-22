package com.app.millennium.data.repository.remote.firebase.auth_provider

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface AuthProvider {


    /**
     * Metodo para crear una cuenta nueva
     */
    suspend fun createAccount (email: String, password: String) : Task<AuthResult>

    /**
     * Metodo para iniciar sesion con email y contraseña
     * que devuelve la tarea a desempeñar
     */
    suspend fun signInWithEmailAndPassword (email: String, password: String) : Task<AuthResult>

    /**
     * Metodo para iniciar sesion con google
     */
    suspend fun signInGoogle(idToken: String) : Task<AuthResult>

    /**
     * Metodo para cerrar sesion
     */
    suspend fun signOut()

    /**
     * Metodo para obtener la sesion actual del usuario
     * que ha iniciado previamente sesion
     */
    suspend fun getCurrentSession () : FirebaseUser?

    /**
     * Metodo que devuelve el id del usuario que ha iniciado sesion
     */
    suspend fun getId(): String?

    /**
     * Metodo que devuelve el email del usuario que ha iniciado sesion
     */
    suspend fun getEmail(): String?

    /**
     * Metodo que devuelve el displayName del usuario que ha iniciado sesion
     */
    suspend fun getDisplayName(): String?
}