package com.app.millennium.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.domain.use_case.product_db.GetAllProductsUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.launch

class HomeFragmentViewModel: ViewModel() {

    //Caso de uso para obtener todos los productos
    private val getAllProductsUseCase = GetAllProductsUseCase()

    //Live data obtener productos
    private val _getAllProducts = MutableLiveData<Task<QuerySnapshot>>()
    val getAllProducts: LiveData<Task<QuerySnapshot>> get() = _getAllProducts

    fun init (){

        viewModelScope.launch {
            _getAllProducts.postValue(
                getAllProductsUseCase.invoke()
            )
        }
    }
}