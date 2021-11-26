package com.app.millennium.ui.adapters.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.millennium.R
import com.app.millennium.data.model.Product

class ProductAdapter(
    private val products : List<Product>
) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(
            R.layout.item_list_product,
            parent,
            false
        )
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.loadData(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    //Metodo para filtrar en la lista
    private fun filter(s: String){

    }
}