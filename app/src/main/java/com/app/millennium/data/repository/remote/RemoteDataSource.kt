package com.app.millennium.data.repository.remote

import com.app.millennium.data.repository.remote.api_rest.ApiRestDataSource
import com.app.millennium.data.repository.remote.firebase.FirebaseDataSource

/**
 * Objeto para obtener los datos en remoto
 */
object RemoteDataSource{
    val firebase = FirebaseDataSource
    val apiRest = ApiRestDataSource
}