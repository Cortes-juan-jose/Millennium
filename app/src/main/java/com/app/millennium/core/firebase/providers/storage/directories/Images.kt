package com.app.millennium.core.firebase.providers.storage.directories

import com.app.millennium.core.common.Constant
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

/**
 * Objeto que devuelve el objeto con el cual se va a almacenar imagenes
 * en el directorio imaegs del cloud storage
 */
object Images {
    val storage = Firebase.storage.reference.child(Constant.STORAGE_IMAGES)
}