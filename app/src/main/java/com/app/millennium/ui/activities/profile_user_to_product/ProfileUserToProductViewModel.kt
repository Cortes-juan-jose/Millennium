package com.app.millennium.ui.activities.profile_user_to_product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.domain.use_case.user_auth.GetIdUseCase
import kotlinx.coroutines.launch

class ProfileUserToProductViewModel : ViewModel() {

    //Caso de uso para obtener el id del usuario de la sesion
    private val getIdUseCase = GetIdUseCase()

    //Live data obtener el id del usuairo de la sesion
    private val _getIdUserToSession = MutableLiveData<String?>()
    val getIdUserToSession: LiveData<String?> get() = _getIdUserToSession

    //Metodo obtener id del usuario de la sesion
    fun getIdUserToSession(){
        viewModelScope.launch {
            _getIdUserToSession.postValue(
                getIdUseCase.invoke()
            )
        }
    }
}