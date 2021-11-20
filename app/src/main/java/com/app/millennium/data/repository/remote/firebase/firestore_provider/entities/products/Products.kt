package com.app.millennium.data.repository.remote.firebase.firestore_provider.entities.products

import com.app.millennium.data.model.Product
import com.google.android.gms.tasks.Task

interface Products {
    suspend fun save(product: Product) : Task<Void>
}