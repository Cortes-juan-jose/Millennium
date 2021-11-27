package com.app.millennium.data.model

import java.util.*

data class Opinion(
    var id: String = UUID.randomUUID().toString(),
    var idUserCreator: String? = null,
    var idUserReceptor: String? = null,
    var msg: String? = null,
    var assessment: Float = 0.0f,
    var timestamp: Long = 0
)
