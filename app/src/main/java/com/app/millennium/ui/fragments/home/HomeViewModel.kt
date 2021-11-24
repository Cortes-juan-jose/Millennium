package com.app.millennium.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.domain.use_case.product_db.GetAllProductsUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    //Caso de uso para obtener todos los productos
    private val getAllProductsUseCase = GetAllProductsUseCase()

    //Live data obtener productos
    private val _getAllProducts = MutableLiveData<Task<QuerySnapshot>>()
    val getAllProducts: LiveData<Task<QuerySnapshot>> get() = _getAllProducts

    //Live data para quitar progress bar
    private val _visibilityProgressBar = MutableLiveData<Boolean>()
    val visibilityProgressBar: LiveData<Boolean> get() = _visibilityProgressBar

    fun init (){

        viewModelScope.launch {
            _visibilityProgressBar.postValue(true)
            _getAllProducts.postValue(
                getAllProductsUseCase.invoke()
            )
            _visibilityProgressBar.postValue(false)
        }
    }
}