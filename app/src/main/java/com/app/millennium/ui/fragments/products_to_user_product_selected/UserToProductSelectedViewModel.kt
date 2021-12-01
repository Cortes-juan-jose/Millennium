package com.app.millennium.ui.fragments.products_to_user_product_selected

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.domain.use_case.product_db.GetAllProductsByUserUseCase
import com.google.firebase.firestore.Query
import kotlinx.coroutines.launch

class UserToProductSelectedViewModel : ViewModel() {

    //Caso de uso que devuelven todos los productos
    private val getAllProductsByUserUseCase = GetAllProductsByUserUseCase()
    
    //Live data para cuando se obtenga todos los productos
    private val _getAllProductsByUser = MutableLiveData<Query>()
    val getAllProductsByUser: LiveData<Query> get() = _getAllProductsByUser

    //Live data para cuando se obtenga todas las opiniones de los demás hacia este usuario
    private val _getOpinionsReceivedByUser = MutableLiveData<Query>()
    val getOpinionsReceivedByUser: LiveData<Query> get() = _getOpinionsReceivedByUser


    //Funcion para obtener los productos
    fun getProducts(idUser: String){
        viewModelScope.launch {
            _getAllProductsByUser.postValue(
                getAllProductsByUserUseCase.invoke(idUser)
            )
        }
    }
    
}