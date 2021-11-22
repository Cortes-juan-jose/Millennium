package com.app.millennium.ui.fragments.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.core.common.convertUser
import com.app.millennium.core.common.isNotNull
import com.app.millennium.data.model.User
import com.app.millennium.domain.use_case.user_auth.GetIdUseCase
import com.app.millennium.domain.use_case.user_auth.SignOutUseCase
import com.app.millennium.domain.use_case.user_db.GetDocumentReferenceUserUseCase
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {

    //Caso de uso para cerrar sesion
    private val signOutUseCase = SignOutUseCase()
    //Caso de uso para obtener el usuario
    private val getDocumentReferenceUserUseCase = GetDocumentReferenceUserUseCase()
    //Caso de uso para obtener el id del usuario
    private val getIdUserUseCase = GetIdUseCase()

    //Live data que devuelve la tarea de la obtención del usuario
    private val _getUser = MutableLiveData<DocumentReference>()
    val getUser: LiveData<DocumentReference> get() = _getUser

    //Live data obtener id usuario
    private val _getIdUser = MutableLiveData<String>()
    val getIdUser: LiveData<String> get() = _getIdUser

    //Live data para construir un objeto user a partir de un map con todos sus propiedades
    private val _buildUser = MutableLiveData<User?>()
    val buildUser: LiveData<User?> get() = _buildUser

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
                getDocumentReferenceUserUseCase.invoke(id)
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

    /**
     * Metodo para construir un usuario a partir de un map
     */
    fun buildUser(data: Map<String, Any>?) {
        val user: User = data?.convertUser()!!
        if (user.isNotNull())
            _buildUser.postValue(user)
    }
}