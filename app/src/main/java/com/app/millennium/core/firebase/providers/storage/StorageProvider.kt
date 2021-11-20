package com.app.millennium.core.firebase.providers.storage

import com.app.millennium.core.firebase.providers.storage.directories.Images

/**
 * Objeto para obtener el almacenamiento de cloud storage de firebase en el
 * que se declaran todas las referencias al almacenamiento
 */
object StorageProvider {
    val storage_images = Images.storage
}