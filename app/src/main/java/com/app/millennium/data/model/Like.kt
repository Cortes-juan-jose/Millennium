package com.app.millennium.data.model

data class Like(
    var id: String? = null,
    var idUserToSession: String? = null,
    var idUserToPostProduct: String? = null,
    var idProduct: String? = null,
    var timestamp: Long = 0
)
