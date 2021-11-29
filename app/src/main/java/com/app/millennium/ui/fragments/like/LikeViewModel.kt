package com.app.millennium.ui.fragments.like

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.core.common.isNotNull
import com.app.millennium.data.model.Product
import com.app.millennium.domain.use_case.likes_db.GetAllLikeByUserUseCase
import com.app.millennium.domain.use_case.product_db.GetProductUseCase
import com.app.millennium.domain.use_case.user_auth.GetIdUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.launch

class LikeViewModel : ViewModel(){

    /**
     * Caso de uso para obtener el id del usuario de la sesion
     */
    private val getIdUseCase = GetIdUseCase()

    /**
     * Caso de uso para obtener todos los likes de un usuario
     */
    private val getAllLikeByUserUseCase = GetAllLikeByUserUseCase()

    /**
     * Caso de uso para obtener 1 producto
     */
    private val getProductUseCase = GetProductUseCase()

    /**
     * Live data para obtener el id del usuario de la sesion
     */
    private val _getIdUserSession = MutableLiveData<String>()
    val getIdUserSession: LiveData<String> get() = _getIdUserSession

    /**
     * Live data para obtener todos los likes del usuario de la sesion
     */
    private val _getAllLikeByUser = MutableLiveData<Task<QuerySnapshot>>()
    val getAllLikeByUser: LiveData<Task<QuerySnapshot>> get() = _getAllLikeByUser

    /**
     * Obtener todos los likes de un usuario
     */
    fun getAllLikeByUser(idUser: String){
        viewModelScope.launch {
            _getAllLikeByUser.postValue(
                getAllLikeByUserUseCase.invoke(idUser)
            )
        }
    }

    /**
     * Obtener el id del usuario de la sesion
     */
    fun getIdUserSession(){
        viewModelScope.launch {
            _getIdUserSession.postValue(
                getIdUseCase.invoke()
            )
        }
    }

    /**
     * Este caso de uso se devuelve directamente para realizar las operaciones de la consulta
     * en sincrono y no asincrono ya que estamos llenando una lista de productos 1 por 1
     */
    fun getProduct(): GetProductUseCase =
        getProductUseCase
}