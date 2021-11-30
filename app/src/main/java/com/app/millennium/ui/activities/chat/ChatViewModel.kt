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
import com.app.millennium.domain.use_case.messages_db.GetAllMessagesByChatBySenderUseCase
import com.app.millennium.domain.use_case.messages_db.GetAllMessagesByChatUseCase
import com.app.millennium.domain.use_case.messages_db.UpdateMessageViewedUseCase
import com.app.millennium.domain.use_case.user_auth.GetIdUseCase
import com.app.millennium.domain.use_case.user_db.GetUserUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
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

    //Caso de uso para obtener todos los mensajes de un chat
    private val getAllMessagesByChatUseCase = GetAllMessagesByChatUseCase()

    //Caso de uso para obtener todos los mensajes de un chat y un sender al mismo tiempo
    private val getAllMessagesByChatBySenderUseCase = GetAllMessagesByChatBySenderUseCase()

    //Caso de uso para actualizar el campo viewed del mensaje
    private val updateMessageViewedUseCase = UpdateMessageViewedUseCase()

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

    //Live data para obtener todos los mensajes de un chat
    private val _getAllMessagesByChat = MutableLiveData<Query>()
    val getAllMessagesByChat: LiveData<Query> get() = _getAllMessagesByChat

    //Live data obtener los mensajes del usuario que envia de un chat
    private val _getAllMessagesByChatBySender = MutableLiveData<Task<QuerySnapshot>>()
    val getAllMessagesByChatBySender: LiveData<Task<QuerySnapshot>> get() = _getAllMessagesByChatBySender

    //Live data actualiza viewed mensaje
    private val _updateMessageViewed = MutableLiveData<Task<Void>>()
    val updateMessageViewed: LiveData<Task<Void>> get() = _updateMessageViewed

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

    //Metodo para obtener todos los mensajes de un chat
    fun getAllMessagesByChat(idChat: String){
        viewModelScope.launch {
            _getAllMessagesByChat.postValue(
                getAllMessagesByChatUseCase.invoke(idChat)
            )
        }
    }

    //Metodo obtener todos los mensajes de un sender de unchat
    fun getAllMessagesByChatBySender(idChat: String, idSender: String){
        viewModelScope.launch {
            _getAllMessagesByChatBySender.postValue(
                getAllMessagesByChatBySenderUseCase.invoke(
                    idChat, idSender
                )
            )
        }
    }

    //Metodo actualizar el campo viewed del mensaje
    fun updateMessageViewed(idMessage: String, state: Boolean){
        viewModelScope.launch {
            _updateMessageViewed.postValue(
                updateMessageViewedUseCase.invoke(
                    idMessage, state
                )
            )
        }
    }
}