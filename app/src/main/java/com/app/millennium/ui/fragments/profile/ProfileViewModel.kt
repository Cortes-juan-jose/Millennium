package com.app.millennium.ui.fragments.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.domain.use_case.user_auth.GetIdUseCase
import com.app.millennium.domain.use_case.user_auth.SignOutUseCase
import com.app.millennium.domain.use_case.user_db.GetUserUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {

    //Caso de uso para cerrar sesion
    private val signOutUseCase = SignOutUseCase()
    //Caso de uso para obtener el usuario
    private val getUserUseCase = GetUserUseCase()
    //Caso de uso para obtener el id del usuario
    private val getIdUserUseCase = GetIdUseCase()

    //Live data que devuelve la tarea de la obtención del usuario
    private val _getUser = MutableLiveData<Task<DocumentSnapshot>>()
    val getUser: LiveData<Task<DocumentSnapshot>> get() = _getUser
    //Live data obtener id usuario
    private val _getIdUser = MutableLiveData<String>()
    val getIdUser: LiveData<String> get() = _getIdUser

    /**
     * Metodo para cerrar sesion
     */
    fun signOut(){
        viewModelScope.launch {
            signOutUseCase.invoke()
        }
    }

    /**
     * Metodo para obtener el usuario
     */
    fun getUser(id: String){
        viewModelScope.launch {
            _getUser.postValue(
                getUserUseCase.invoke(id)
            )
        }
    }

    /**
     * Metodo para obtener el id de inicio de sesion
     */
    fun getIdUser(){
        viewModelScope.launch {
            _getIdUser.postValue(
                getIdUserUseCase.invoke()
            )
        }
    }
}