package com.app.millennium.ui.activities.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.data.model.Chat
import com.app.millennium.domain.use_case.chat_db.CreateChatUserToChatUseCase
import com.app.millennium.domain.use_case.chat_db.CreateChatUserToSessionUseCase
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel(){

    //Caso de uso para crear el chat al usuario de la sesion
    private val createChatUserToSessionUseCase = CreateChatUserToSessionUseCase()

    //Caso de uso para crear el chat al usuario del chat
    private val createChatUserToChatUseCase = CreateChatUserToChatUseCase()

    //live data crear chat usuario sesion
    private val _createChatUserToSession = MutableLiveData<Task<Void>>()
    val createChatUserToSession: LiveData<Task<Void>> get() = _createChatUserToSession

    //Live data crear chat usuario del chat
    private val _createChatUserToChat = MutableLiveData<Task<Void>>()
    val createChatUserToChat: LiveData<Task<Void>> get() = _createChatUserToChat

    //Metodo crear chat usuario sesion
    fun createChatUserToSession(chat: Chat){
        viewModelScope.launch {
            _createChatUserToSession.postValue(
                createChatUserToSessionUseCase.invoke(chat)
            )
        }
    }

    //Metodo crear chat usuario del chat
    fun createChatUserToChat(chat: Chat){
        viewModelScope.launch {
            _createChatUserToChat.postValue(
                createChatUserToChatUseCase.invoke(chat)
            )
        }
    }
}