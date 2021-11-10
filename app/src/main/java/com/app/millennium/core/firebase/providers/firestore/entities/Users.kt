package com.app.millennium.core.firebase.providers.firestore.entities

import android.annotation.SuppressLint
import com.app.millennium.core.common.Constant.COLLECTION_USERS
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * Objeto que hace referencia a la coleccion users
 */
object Users {
    @SuppressLint("StaticFieldLeak")
    val db = Firebase.firestore.collection(COLLECTION_USERS)
}