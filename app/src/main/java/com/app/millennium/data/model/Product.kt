package com.app.millennium.data.model

import java.util.*

data class Product(
    var id: String = UUID.randomUUID().toString(),
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
)
