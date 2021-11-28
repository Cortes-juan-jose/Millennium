package com.app.millennium.ui.activities.product_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.domain.use_case.user_auth.GetIdUseCase
import com.app.millennium.domain.use_case.user_db.GetUserUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.launch

class ProductDetailViewModel : ViewModel() {

    //Caso de uso para obtener el usuario
    private val getUserUseCase = GetUserUseCase()

    //caso de uso para obtener el id del usuario de la sesion
    private val getIdUseCase = GetIdUseCase()

    //Live data obtener usuario
    private val _getUser = MutableLiveData<Task<DocumentSnapshot>>()
    val getUser: LiveData<Task<DocumentSnapshot>> get() = _getUser

    //Live data obtener id usuario de la sesion
    private val _getIdUserSession = MutableLiveData<String>()
    val getIdUserSession: LiveData<String> get() = _getIdUserSession

    /**
     * Metodo para obtener el usuario mediante el id
     */
    fun getUser(id: String){
        viewModelScope.launch {
            _getUser.postValue(
                getUserUseCase.invoke(id)
            )
        }
    }

    /**
     * Metodo para obtener el id del usuario de la sesion
     */
    fun getIdUserSession(){
        viewModelScope.launch {
            _getIdUserSession.postValue(
                getIdUseCase.invoke()
            )
        }
    }
}