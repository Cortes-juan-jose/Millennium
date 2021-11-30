package com.app.millennium.ui.activities.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.data.model.Chat
import com.app.millennium.domain.use_case.chat_db.CreateChatUseCase
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel(){

    //Caso de uso para crear el chat al usuario de la sesion
    private val createChatUseCase = CreateChatUseCase()

    //live data crear chat usuario sesion
    private val _createChat = MutableLiveData<Task<Void>>()
    val createChat: LiveData<Task<Void>> get() = _createChat

    //Metodo crear chat usuario sesion
    fun createChat(chat: Chat){
        viewModelScope.launch {
            _createChat.postValue(
                createChatUseCase.invoke(chat)
            )
        }
    }
}