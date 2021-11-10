package com.app.millennium.ui.activities.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.data.model.User
import com.app.millennium.domain.use_case.user_auth.*
import com.app.millennium.domain.use_case.user_db.SaveUserUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    /**
     * Casos de uso
     */
    private val signInEmailAndPasswordUseCase = SignInEmailAndPasswordUseCase()
    private val signInGoogleUseCase = SignInGoogleUseCase()
    private val getIdUseCase = GetIdUseCase()
    private val getEmailUseCase = GetEmailUseCase()
    private val getDisplayNameUseCase = GetDisplayNameUseCase()
    private val saveUserUseCase = SaveUserUseCase()

    /**
     * LiveData (Observables)
     */
    private val _signInWithEmailAndPassword = MutableLiveData<Task<AuthResult>>()
    val signInWithEmailAndPassword: LiveData<Task<AuthResult>> get() = _signInWithEmailAndPassword

    private val _signInGoogle = MutableLiveData<Task<AuthResult>>()
    val signInGoogle: LiveData<Task<AuthResult>> get() = _signInGoogle

    private val _saveUser = MutableLiveData<Task<Void>?>()
    val saveUser: LiveData<Task<Void>?> get() = _saveUser

    private val _getId = MutableLiveData<String>()
    val getId: LiveData<String> get() = _getId

    private val _getEmail = MutableLiveData<String>()
    val getEmail: LiveData<String> get() = _getEmail

    private val _getDisplayName = MutableLiveData<String>()
    val getDisplayName: LiveData<String> get() = _getDisplayName

    /**
     * Funcion para iniciar sesion
     */
    fun signInEmailPassword(email: String, password: String){

        viewModelScope.launch {
            _signInWithEmailAndPassword.postValue(
                signInEmailAndPasswordUseCase.invoke(email, password)
            )
        }
    }

    /**
     * Funcion para iniciar sesión con google
     */
    fun signInGoogle(idToken: String) {

        if (idToken.isNotEmpty()){
            viewModelScope.launch {
                _signInGoogle.postValue(
                    signInGoogleUseCase.invoke(idToken)
                )
            }
        }
    }

    /**
     * Funcion para guardar el usuario en la db Collection users
     */
    fun saveUser(user: User){
        viewModelScope.launch {
            _saveUser.postValue(
                saveUserUseCase.invoke(user)
            )
        }
    }

    /**
     * Funcion para obtener el id del inicio de sesion
     */
    fun getId(){
        viewModelScope.launch {
            _getId.postValue(
                getIdUseCase.invoke()
            )
        }
    }

    /**
     * Funcion para obtener el email del inicio de sesion
     */
    fun getEmail(){
        viewModelScope.launch {
            _getEmail.postValue(
                getEmailUseCase.invoke()
            )
        }
    }

    /**
     * Función para obtener el displayName del inicio de sesión
     */
    fun getDisplayName(){
        viewModelScope.launch {
            _getDisplayName.postValue(
                getDisplayNameUseCase.invoke()
            )
        }
    }
}