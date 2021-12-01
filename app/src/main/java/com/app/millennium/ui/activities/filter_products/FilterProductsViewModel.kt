package com.app.millennium.ui.activities.filter_products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.domain.use_case.product_db.GetAllProductsByCategoryUseCase
import com.google.firebase.firestore.Query
import kotlinx.coroutines.launch

class FilterProductsViewModel : ViewModel() {

    private val getAllProductsByCategoryUseCase = GetAllProductsByCategoryUseCase()

    private val _getAllProductsByCategory = MutableLiveData<Query>()
    val getAllProductsByCategory: LiveData<Query> get() = _getAllProductsByCategory

    fun getAllProductsByCategory(category: String){
        viewModelScope.launch {
            _getAllProductsByCategory.postValue(
                getAllProductsByCategoryUseCase.invoke(category)
            )
        }
    }
}