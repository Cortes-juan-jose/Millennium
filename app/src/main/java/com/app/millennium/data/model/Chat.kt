package com.app.millennium.data.model

data class Chat(
    var idUserToSession: String? = null,
    var idUserToChat: String? = null,
    var isWriting: Boolean = false,
    var timestamp: Long = 0L
)
