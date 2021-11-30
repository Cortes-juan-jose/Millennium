package com.app.millennium.ui.activities.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.data.model.Chat
import com.app.millennium.data.model.Message
import com.app.millennium.domain.use_case.chat_db.CreateChatUseCase
import com.app.millennium.domain.use_case.chat_db.GetChatByUserToSessionByUserToChatUseCase
import com.app.millennium.domain.use_case.messages_db.CreateMessageUseCase
import com.app.millennium.domain.use_case.user_auth.GetIdUseCase
import com.app.millennium.domain.use_case.user_db.GetUserUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel(){

    //Caso de uso para crear el chat al usuario de la sesion
    private val createChatUseCase = CreateChatUseCase()

    //Caso de uso obtener chat especifico
    private val getChatByUserToSessionByUserToChatUseCase = GetChatByUserToSessionByUserToChatUseCase()

    //Caso de uso para crear un message
    private val createMessageUseCase = CreateMessageUseCase()

    //Caso de uso para obtener el id del usuario de la sesion
    private val getIdUseCase = GetIdUseCase()

    //Caso de uso para obtener un usuario
    private val getUserUseCase = GetUserUseCase()

    //live data crear chat usuario sesion
    private val _createChat = MutableLiveData<Task<Void>>()
    val createChat: LiveData<Task<Void>> get() = _createChat

    //Live data obtener chat especifico
    private val _getChatByUserToSessionByUserToChat = MutableLiveData<Task<QuerySnapshot>>()
    val getChatByUserToSessionByUserToChat: LiveData<Task<QuerySnapshot>> get() = _getChatByUserToSessionByUserToChat

    //Live data crear un message
    private val _createMessage = MutableLiveData<Task<Void>>()
    val createMessage: LiveData<Task<Void>> get() = _createMessage

    //Live data obtener id del usuario de la sesion
    private val _getIdUserToSession = MutableLiveData<String?>()
    val getIdUserToSession: LiveData<String?> get() = _getIdUserToSession

    //Live data para obtener el usuario por id
    private val _getUserById = MutableLiveData<Task<DocumentSnapshot>>()
    val getUserById: LiveData<Task<DocumentSnapshot>> get() = _getUserById

    //Metodo crear chat usuario sesion
    fun createChat(chat: Chat){
        viewModelScope.launch {
            _createChat.postValue(
                createChatUseCase.invoke(chat)
            )
        }
    }

    //Metodo para obtener chat especifico
    fun getChatByUserToSessionByUserToChat(idUserToSession: String, idUserToChat: String){
        viewModelScope.launch {
            _getChatByUserToSessionByUserToChat.postValue(
                getChatByUserToSessionByUserToChatUseCase.invoke(idUserToSession, idUserToChat)
            )
        }
    }

    //Metodo para crear un message
    fun createMessage(msg: Message){
        viewModelScope.launch {
            _createMessage.postValue(
                createMessageUseCase.invoke(msg)
            )
        }
    }

    //Metodo obtener el id del usuario de la sesion
    fun getIdUserToSession(){
        viewModelScope.launch {
            _getIdUserToSession.postValue(
                getIdUseCase.invoke()
            )
        }
    }

    //Metodo para obtener un usuario por id
    fun getUserById(id: String){
        viewModelScope.launch {
            _getUserById.postValue(
                getUserUseCase.invoke(id)
            )
        }
    }
}