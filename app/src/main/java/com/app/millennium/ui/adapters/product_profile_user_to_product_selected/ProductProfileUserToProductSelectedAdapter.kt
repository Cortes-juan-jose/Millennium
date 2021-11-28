package com.app.millennium.ui.adapters.product_profile_user_to_product_selected

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.millennium.R
import com.app.millennium.data.model.Product

class ProductProfileUserToProductSelectedAdapter(
    private val products : List<Product>
) : RecyclerView.Adapter<ProductProfileUserToProductSelectedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductProfileUserToProductSelectedViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(
            R.layout.item_list_product_profile_user_to_product_selected,
            parent,
            false
        )
        return ProductProfileUserToProductSelectedViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ProductProfileUserToProductSelectedViewHolder, position: Int) {
        holder.loadData(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }
}