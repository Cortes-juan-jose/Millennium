package com.app.millennium.ui.adapters.product_home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.millennium.R
import com.app.millennium.data.model.Product

class ProductHomeAdapter(
    private val products : List<Product>,
) : RecyclerView.Adapter<ProductHomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHomeViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(
            R.layout.item_list_product_home,
            parent,
            false
        )
        return ProductHomeViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holderHome: ProductHomeViewHolder, position: Int) {
        holderHome.loadData(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }
}