package com.app.millennium.core.firebase.providers.firestore.entities

import com.app.millennium.core.common.Constant
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * Objeto que hace referencia a la coleccion products
 */
object Products {
    val db = Firebase.firestore.collection(Constant.COLLECTION_PRODUCTS)
}