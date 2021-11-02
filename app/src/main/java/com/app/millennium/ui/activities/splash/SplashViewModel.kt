package com.app.millennium.ui.activities.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.domain.use_case.user_auth.GetCurrentSessionUseCase
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val getCurrentSessionUseCase = GetCurrentSessionUseCase()

    private val _getCurrentSessionLiveData = MutableLiveData<FirebaseUser>()
    val getCurrentSessionLiveData: LiveData<FirebaseUser> get() = _getCurrentSessionLiveData


    fun getCurrentSession(){
        viewModelScope.launch {
            _getCurrentSessionLiveData.postValue(getCurrentSessionUseCase.invoke())
        }
    }
}