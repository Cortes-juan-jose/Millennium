package com.app.millennium.ui.adapters.product_profile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.millennium.R
import com.app.millennium.data.model.Product

class ProductProfileAdapter(
    private val products : List<Product>,
    private val context: Context
) : RecyclerView.Adapter<ProductProfileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductProfileViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(
            R.layout.item_list_product_profile,
            parent,
            false
        )
        return ProductProfileViewHolder(view, context)
    }

    override fun onBindViewHolder(holderProfile: ProductProfileViewHolder, position: Int) {
        holderProfile.loadData(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }
}