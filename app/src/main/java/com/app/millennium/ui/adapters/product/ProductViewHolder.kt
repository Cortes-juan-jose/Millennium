package com.app.millennium.ui.adapters.product

import android.net.Uri
import android.view.View
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.app.millennium.core.common.isNull
import com.app.millennium.core.utils.RelativeTime
import com.app.millennium.data.model.Product
import com.app.millennium.databinding.ItemListProductBinding
import com.squareup.picasso.Picasso

class ProductViewHolder(
    private val view : View
) : RecyclerView.ViewHolder(view){

    private val binding: ItemListProductBinding = ItemListProductBinding.bind(view)

    fun init(product: Product){
        //Seteamos todos los campos en la vista
        Picasso.get().load(getFirtsImageProductNotNull(product)).into(binding.ivProduct)
        binding.mtvTitle.text = product.title
        binding.mtvPrice.text = product.price.toString()
        binding.mtvTimestamp.text = RelativeTime.getTimeAgo(product.timestamp, view.context)
    }

    /**
     * Metodo que devuelve la uri de la primera imagen del producto que no
     * sea nulo para setearsela al iv_product
     */
    private fun getFirtsImageProductNotNull(product: Product): Uri? {
        product.image1?.let { return it.toUri() }
        product.image2?.let { return it.toUri() }
        product.image3?.let { return it.toUri() }
        product.image4?.let { return it.toUri() }
        //Debemos indicar que devuelve nulo porque así lo requiere kotlin
        //Pero al subir el producto a firestore ya está configurado para
        //que al menos se guarde una imagen, por lo tanto nunca será nulo
        return null
    }

}