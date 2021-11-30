package com.app.millennium.ui.fragments.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.domain.use_case.chat_db.GetChatByUserToSessionByUserToChatUseCase
import com.app.millennium.domain.use_case.user_auth.GetIdUseCase
import com.google.firebase.firestore.Query
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    //Caso de uso para obtener todos los chats del usuario de la sesion

    //Caso de uso para obtener el id del usuario de la sesion
    private val getIdUseCase = GetIdUseCase()
    
    //Live data obtener chats usuario sesion

    //Live data obtener id del usuario de la sesion
    private val _getIdUserToSession = MutableLiveData<String?>()
    val getIdUserToSession: LiveData<String?> get() = _getIdUserToSession

    //Metodo obtener todos los chats del usuario de la sesion

    //Metodo obtener el id del usuario de la sesion
    fun getIdUserToSession(){
        viewModelScope.launch {
            _getIdUserToSession.postValue(
                getIdUseCase.invoke()
            )
        }
    }

}