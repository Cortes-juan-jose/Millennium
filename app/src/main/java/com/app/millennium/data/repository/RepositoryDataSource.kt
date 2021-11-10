package com.app.millennium.data.repository

import com.app.millennium.data.repository.remote.RemoteDataSource

/**
 * Objeto repositorio para poder obtener datos tanto en remoto como en local
 */
object RepositoryDataSource {
    val remote: RemoteDataSource = RemoteDataSource
}