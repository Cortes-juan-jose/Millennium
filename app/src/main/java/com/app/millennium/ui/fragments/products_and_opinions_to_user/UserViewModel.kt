package com.app.millennium.ui.fragments.products_and_opinions_to_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.domain.use_case.opinion_db.GetOpinionsReceivedByUserUseCase
import com.app.millennium.domain.use_case.product_db.GetAllProductsByUserUseCase
import com.app.millennium.domain.use_case.user_auth.GetIdUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    //Caso de uso para obtener el id del usuario
    private val getIdUseCase = GetIdUseCase()

    //Caso de uso que devuelven todos los productos
    private val getAllProductsByUserUseCase = GetAllProductsByUserUseCase()

    //caso de uso que devuelven todas las opiniones que le han hecho al usuario
    private val getOpinionsReceivedByUserUseCase = GetOpinionsReceivedByUserUseCase()

    private val _getIdUserToSession = MutableLiveData<String>()
    val getIdUserToSession: LiveData<String> get() = _getIdUserToSession
    
    //Live data para cuando se obtenga todos los productos
    private val _getAllProductsByUser = MutableLiveData<Query>()
    val getAllProductsByUser: LiveData<Query> get() = _getAllProductsByUser

    //Live data para cuando se obtenga todas las opiniones de los demás hacia este usuario
    private val _getOpinionsReceivedByUser = MutableLiveData<Query>()
    val getOpinionsReceivedByUser: LiveData<Query> get() = _getOpinionsReceivedByUser

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

    //Funcion para obtener las opiniones
    fun getOpinions(idUser: String){
        viewModelScope.launch {
            _getOpinionsReceivedByUser.postValue(
                getOpinionsReceivedByUserUseCase.invoke(idUser)
            )
        }
    }
    
}