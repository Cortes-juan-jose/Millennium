package com.app.millennium.data.repository.remote.firebase.storage_provider.directories.images

import com.app.millennium.core.common.Constant
import com.app.millennium.core.firebase.FirebaseProvider
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.util.*

class ImagesImpl: Images {

    //Referencia al almacenamiento de las imagenes
    private val storage: StorageReference = FirebaseProvider.storage_images

    /**
     * Metodo para guardar una imagen en el almacenamiento de firebase
     */
    override suspend fun save(imageByteArray: ByteArray): UploadTask =
        storage.child("${Date()}${Constant.FORMAT_JPEG}").putBytes(imageByteArray)
}