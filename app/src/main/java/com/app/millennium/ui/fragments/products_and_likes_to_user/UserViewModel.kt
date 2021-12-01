package com.app.millennium.ui.fragments.products_and_likes_to_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.domain.use_case.likes_db.GetAllLikeByUserUseCase
import com.app.millennium.domain.use_case.product_db.GetAllProductsByUserUseCase
import com.app.millennium.domain.use_case.product_db.GetProductUseCase
import com.app.millennium.domain.use_case.user_auth.GetIdUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    //Caso de uso para obtener el id del usuario
    private val getIdUseCase = GetIdUseCase()

    //Caso de uso que devuelven todos los productos
    private val getAllProductsByUserUseCase = GetAllProductsByUserUseCase()

    private val _getIdUserToSession = MutableLiveData<String>()
    val getIdUserToSession: LiveData<String> get() = _getIdUserToSession
    
    //Live data para cuando se obtenga todos los productos
    private val _getAllProductsByUser = MutableLiveData<Query>()
    val getAllProductsByUser: LiveData<Query> get() = _getAllProductsByUser

    //Live data para cuando se obtenga todas las opiniones de los demás hacia este usuario
    private val _getOpinionsReceivedByUser = MutableLiveData<Query>()
    val getOpinionsReceivedByUser: LiveData<Query> get() = _getOpinionsReceivedByUser

    /**
     * Caso de uso para obtener todos los likes de un usuario
     */
    private val getAllLikeByUserUseCase = GetAllLikeByUserUseCase()

    /**
     * Caso de uso para obtener 1 producto
     */
    private val getProductUseCase = GetProductUseCase()

    /**
     * Live data para obtener todos los likes del usuario de la sesion
     */
    private val _getAllLikeByUser = MutableLiveData<Task<QuerySnapshot>>()
    val getAllLikeByUser: LiveData<Task<QuerySnapshot>> get() = _getAllLikeByUser

    //Funcion para obtener el id del usuario
    fun getIdUserToSession(){
        viewModelScope.launch {
            _getIdUserToSession.postValue(
                getIdUseCase.invoke()
            )
        }
    }

    //Funcion para obtener los productos
    fun getProducts(idUser: String){
        viewModelScope.launch {
            _getAllProductsByUser.postValue(
                getAllProductsByUserUseCase.invoke(idUser)
            )
        }
    }

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
     * Este caso de uso se devuelve directamente para realizar las operaciones de la consulta
     * en sincrono y no asincrono ya que estamos llenando una lista de productos 1 por 1
     */
    fun getProduct(): GetProductUseCase =
        getProductUseCase
    
}