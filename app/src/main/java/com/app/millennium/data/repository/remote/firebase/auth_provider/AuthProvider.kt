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
     * Metodo para obtener la sesion actual del usuario
     * que ha iniciado previamente sesion
     */
    suspend fun getCurrentSession () : FirebaseUser?
}