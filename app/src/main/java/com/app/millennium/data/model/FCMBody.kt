package com.app.millennium.data.model

/**
 * Los campos del json que se va a ejecutar cuando enviemos una notificacion
 */
data class FCMBody(
    var to: String? = null,
    var priority: String? = null,
    var ttl: String? = null,
    var data: Map<String, String>? = null
)
