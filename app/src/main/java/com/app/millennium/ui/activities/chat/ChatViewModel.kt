package com.app.millennium.ui.activities.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.data.model.Chat
import com.app.millennium.domain.use_case.chat_db.CreateChatUseCase
import com.app.millennium.domain.use_case.chat_db.GetChatByUserToSessionByUserToChatUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel(){

    //Caso de uso para crear el chat al usuario de la sesion
    private val createChatUseCase = CreateChatUseCase()

    //Caso de uso obtener chat especifico
    private val getChatByUserToSessionByUserToChatUseCase = GetChatByUserToSessionByUserToChatUseCase()

    //live data crear chat usuario sesion
    private val _createChat = MutableLiveData<Task<Void>>()
    val createChat: LiveData<Task<Void>> get() = _createChat

    //Live data obtener chat especifico
    private val _getChatByUserToSessionByUserToChat = MutableLiveData<Task<QuerySnapshot>>()
    val getChatByUserToSessionByUserToChat: LiveData<Task<QuerySnapshot>> get() = _getChatByUserToSessionByUserToChat

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
}