package com.app.millennium.domain.use_case.images_storage

import android.util.Log
import com.app.millennium.data.repository.RepositoryDataSource
import com.google.firebase.storage.UploadTask

/**
 * Caso de uso para guardar una imagen en el almacenamiento de firebase
 */
class SaveImageUseCase {
    //Almacenmaiento de las imagenes
    private val storage = RepositoryDataSource.remote.firebase.storage.images

    /**
     * Metodo que guarda la imagen
     */
    suspend operator fun invoke(imageByteArray: ByteArray): UploadTask =
        storage.save(imageByteArray)

}