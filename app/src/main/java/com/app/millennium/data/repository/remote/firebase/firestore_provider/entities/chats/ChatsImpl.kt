package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.chats

import com.app.millennium.core.common.Constant
import com.app.millennium.core.firebase.FirebaseProvider
import com.app.millennium.data.model.Chat
import com.google.android.gms.tasks.Task

class ChatsImpl: Chats {

    private val db = FirebaseProvider.chatsCollection

    //Crear chat para el usuario de la sesion
    override suspend fun createChatUserToSession(chat: Chat): Task<Void> =
        db.document(chat.idUserToSession!!)
            .collection(Constant.COLLECTION_USERS)
            .document(chat.idUserToChat!!)
            .set(chat)

    //Crear chat para el usuario del chat
    override suspend fun createChatUserToChat(chat: Chat): Task<Void> =
        db.document(chat.idUserToChat!!)
            .collection(Constant.COLLECTION_USERS)
            .document(chat.idUserToSession!!)
            .set(chat)
}