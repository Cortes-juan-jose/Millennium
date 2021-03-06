package com.app.millennium.ui.activities.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.domain.use_case.token_db.CreateTokenUseCase
import com.app.millennium.domain.use_case.token_db.GetTokenToDeviceUseCase
import com.app.millennium.domain.use_case.user_auth.GetIdUseCase
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.launch

class HomeActivityViewModel : ViewModel(){

    private val getTokenToDeviceUseCase = GetTokenToDeviceUseCase()
    private val createTokenUseCase = CreateTokenUseCase()
    private val getIdUseCase = GetIdUseCase()
    
    private val _getTokenToDevice = MutableLiveData<Task<String>>()
    val getTokenToDevice: LiveData<Task<String>> get() = _getTokenToDevice

    private val _createToken = MutableLiveData<Task<Void>?>()
    val createToken: LiveData<Task<Void>?> get() = _createToken

    private val _getIdUserSession = MutableLiveData<String?>()
    val getIdUserSession: LiveData<String?> get() = _getIdUserSession

    fun getTokenToDevice(){
        viewModelScope.launch {
            _getTokenToDevice.postValue(
                getTokenToDeviceUseCase.invoke()
            )
        }
    }

    fun createToken(idUser: String, tokenToDevice: String){
        viewModelScope.launch {
            _createToken.postValue(
                createTokenUseCase.invoke(idUser, tokenToDevice)
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