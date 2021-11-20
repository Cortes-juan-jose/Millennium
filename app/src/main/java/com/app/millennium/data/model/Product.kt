package com.app.millennium.data.model

data class Product(
    var id: String? = null,
    var idUser: String? = null,
    var title: String? = null,
    var description: String? = null,
    var category: String? = null,
    var price: Double = 0.0,
    var negotiable: String? = null,
    var productStatus: String? = null,
    var image1: String? = null,
    var image2: String? = null,
    var image3: String? = null,
    var image4: String? = null,
    var timestamp: Long = 0L
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
