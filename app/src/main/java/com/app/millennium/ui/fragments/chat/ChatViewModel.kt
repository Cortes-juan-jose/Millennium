package com.app.millennium.ui.fragments.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.domain.use_case.chat_db.GetAllChatsByUserUseCase
import com.app.millennium.domain.use_case.user_auth.GetIdUseCase
import com.google.firebase.firestore.Query
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    //Caso de uso para obtener el id del usuario de la sesion
    private val getIdUseCase = GetIdUseCase()
    
    //Live data obtener chats usuario sesion
    private val getAllChatsByUserUseCase = GetAllChatsByUserUseCase()

    //Live data obtener id del usuario de la sesion
    private val _getIdUserToSession = MutableLiveData<String?>()
    val getIdUserToSession: LiveData<String?> get() = _getIdUserToSession

    //Metodo obtener todos los chats del usuario de la sesion
    private val _getAllChatsByUser = MutableLiveData<Query>()
    val getAllChatsByUser: LiveData<Query> get() = _getAllChatsByUser

    //Metodo obtener el id del usuario de la sesion
    fun getIdUserToSession(){
        viewModelScope.launch {
            _getIdUserToSession.postValue(
                getIdUseCase.invoke()
            )
        }
    }

    //Metodo obtener chats del usuario de la sesion
    fun getAllChatsByUser(idUserToSession: String){
        viewModelScope.launch {
            _getAllChatsByUser.postValue(
                getAllChatsByUserUseCase.invoke(idUserToSession)
            )
        }
    }

}