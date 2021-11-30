package com.app.millennium.data.model

import java.util.ArrayList

data class Chat(
    var id: String? = null,
    var idUserToSession: String? = null,
    var idUserToChat: String? = null,
    var idsUsers: ArrayList<String>? = null,
    var isWriting: Boolean = false,
    var timestamp: Long = 0L
)
