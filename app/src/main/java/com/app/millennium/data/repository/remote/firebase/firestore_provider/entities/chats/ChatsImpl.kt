package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.chats

import android.util.Log
import com.app.millennium.core.common.Constant
import com.app.millennium.core.firebase.FirebaseProvider
import com.app.millennium.data.model.Chat
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class ChatsImpl: Chats {

    private val db = FirebaseProvider.chatsCollection

    //Crear chat para el usuario de la sesion
    override suspend fun create(chat: Chat): Task<Void> =
        db.document(
            chat.idUserToSession.toString()+chat.idUserToChat.toString()
        ).set(chat)

    override suspend fun getChatByUserToSessionByUserToChat(
        idUserToSession: String,
        idUserToChat: String
    ): Task<QuerySnapshot> {
        val formatsIds = arrayListOf<String>(
            idUserToSession+idUserToChat,
            idUserToChat+idUserToSession
        )
        return db.whereIn(Constant.PROP_ID_CHAT, formatsIds).get()
    }

    override suspend fun getAllByUser(idUserToSession: String): Query =
        db.whereArrayContains(Constant.PROP_IDS_USERS_CHAT, idUserToSession)
}