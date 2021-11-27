package com.app.millennium.data.model

import java.util.*

data class Like(
    var id: String = UUID.randomUUID().toString(),
    var idUserToSession: String? = null,
    var idUserToPostProduct: String? = null,
    var idProduct: String? = null,
    var timestamp: Long = 0
)
