package com.app.millennium.data.repository.remote.firebase.storage_provider.directories.images

import android.net.Uri
import com.app.millennium.core.common.Constant
import com.app.millennium.core.firebase.FirebaseProvider
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.util.*

class ImagesImpl: Images {

    //Referencia al almacenamiento de las imagenes
    private val storage: StorageReference = FirebaseProvider.storage_images
    private var refImage: StorageReference? = null

    /**
     * Metodo para guardar una imagen en el almacenamiento de firebase
     */
    override suspend fun save(imageByteArray: ByteArray): UploadTask {
        refImage = storage.child("${Date()}${Constant.FORMAT_JPEG}")
        return refImage!!.putBytes(imageByteArray)
    }

    override suspend fun getUrlImage(): Task<Uri>? =
        refImage?.downloadUrl
}