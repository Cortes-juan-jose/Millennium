package com.app.millennium.domain.use_case.images_storage

import android.net.Uri
import com.app.millennium.data.repository.RepositoryDataSource
import com.google.android.gms.tasks.Task

class GetUrlImageUseCase {
    private val repository = RepositoryDataSource.remote.firebase.storage.images

    suspend operator fun invoke(): Task<Uri>? =
        repository.getUrlImage()
}