package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.opinions

import com.app.millennium.data.model.Opinion
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

interface Opinions {

    suspend fun save(opinion: Opinion) : Task<Void>
    //Metodo que devuelve todas las opiniones que ha realizado un user
    suspend fun getMadeByUser(idUser: String) : Query
    //Metodo que devuelve todas las opiniones que le han publicado
    suspend fun getReceivedByUser(idUser: String) : Query
}