package com.app.millennium.data.repository.remote.firebase.storage_provider.directories.images

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.UploadTask

interface Images {

    //Metodo para guardar una imagen en firebase
    suspend fun save(imageByteArray: ByteArray): UploadTask

    //Metodo para obtener la url de la imagen
    suspend fun getUrlImage(): Task<Uri>?

}
