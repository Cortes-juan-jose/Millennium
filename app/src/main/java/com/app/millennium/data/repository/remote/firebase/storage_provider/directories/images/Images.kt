package com.app.millennium.data.repository.remote.firebase.storage_provider.directories.images

import com.google.firebase.storage.UploadTask
import java.io.File

interface Images {

    //Metodo para guardar una imagen en firebase
    suspend fun save(imageByteArray: ByteArray): UploadTask

}
