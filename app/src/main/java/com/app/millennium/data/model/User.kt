package com.app.millennium.data.model

data class User(
    val id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var imgProfile: String? = null,
    var imgCoverPage: String? = null,
    var uploadedProducts: Int = 0,
    var timestamp: Long = 0L
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}

