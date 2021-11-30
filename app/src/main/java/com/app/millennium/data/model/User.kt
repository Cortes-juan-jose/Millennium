package com.app.millennium.data.model

/**
 * Modelo User
 */
data class User(
    var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var imgProfile: String? = null,
    var imgCover: String? = null,
    var uploadedProducts: Int = 0,
    var timestamp: Long = 0L
)

