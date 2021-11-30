package com.app.millennium.data.repository.remote.firebase.firestore_provider

import com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.chats.ChatsImpl
import com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.likes.LikesImpl
import com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.messages.MessagesImpl
import com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.products.ProductsImpl
import com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.tokens.TokensImpl
import com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.users.UsersImpl

/**
 * Objeto para obtener todas las colecciones
 */
object FirestoreDataSource {
    val users = UsersImpl()
    val products = ProductsImpl()
    val likes = LikesImpl()
    val tokens = TokensImpl()
    val chats = ChatsImpl()
    val messages = MessagesImpl()
}