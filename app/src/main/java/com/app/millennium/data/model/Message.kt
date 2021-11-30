package com.app.millennium.data.model

import java.util.*

data class Message(
    val id: String = UUID.randomUUID().toString(),
    var idSender: String? = null,
    var idReceiver: String? = null,
    var idChat: String? = null,
    var message: String? = null,
    var timestamp: Long = 0L,
    var viewed: Boolean = false
)
