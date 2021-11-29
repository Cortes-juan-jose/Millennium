package com.app.millennium.ui.fragments.like

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.domain.use_case.likes_db.GetAllLikeByUserUseCase
import com.app.millennium.domain.use_case.user_auth.GetIdUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.launch

class LikeViewModel : ViewModel(){

    private val getIdUseCase = GetIdUseCase()

    private val getAllLikeByUserUseCase = GetAllLikeByUserUseCase()

    private val _getIdUserSession = MutableLiveData<String>()
    val getIdUserSession: LiveData<String> get() = _getIdUserSession

    private val _getAllLikeByUser = MutableLiveData<Task<QuerySnapshot>>()
    val getAllLikeByUser: LiveData<Task<QuerySnapshot>> get() = _getAllLikeByUser

    fun loadProducts(idUser: String){
        viewModelScope.launch {
            _getAllLikeByUser.postValue(
                getAllLikeByUserUseCase.invoke(idUser)
            )
        }
    }

    fun getIdUserSession(){
        viewModelScope.launch {
            _getIdUserSession.postValue(
                getIdUseCase.invoke()
            )
        }
    }
}