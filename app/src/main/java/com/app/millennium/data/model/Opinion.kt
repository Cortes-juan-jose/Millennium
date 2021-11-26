package com.app.millennium.data.model

data class Opinion(
    var id: String? = null,
    var idUserCreator: String? = null,
    var idUserReceptor: String? = null,
    var msg: String? = null,
    var assessment: Float = 0.0f,
    var timestamp: Long = 0
)
