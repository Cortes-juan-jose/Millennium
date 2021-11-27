package com.app.millennium.ui.adapters.product_home

import android.content.Context
import android.net.Uri
import android.view.View
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.app.millennium.core.common.Constant
import com.app.millennium.core.common.formatAsPrice
import com.app.millennium.core.common.loadBundle
import com.app.millennium.core.common.openActivity
import com.app.millennium.core.utils.RelativeTime
import com.app.millennium.data.model.Product
import com.app.millennium.databinding.ItemListProductHomeBinding
import com.app.millennium.domain.use_case.likes_db.SaveLikeUseCase
import com.app.millennium.ui.activities.product_detail.ProductDetailActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductHomeViewHolder(
    private val view : View,
    private val context : Context
) : RecyclerView.ViewHolder(view), ProductHomeUsesCases{

    //Binding
    private val binding: ItemListProductHomeBinding = ItemListProductHomeBinding.bind(view)

    //Caso de uso para guardar un like
    override val saveLikeUseCase: SaveLikeUseCase
        get() = SaveLikeUseCase()

    //Producto
    private lateinit var product: Product

    //Cargar el producto
    fun loadData(product: Product){
        this.product = product
        initUI()
    }

    //inicializar la vista
    private fun initUI() {
        //Seteamos todos los campos en la vista
        Picasso.get().load(getFirtsImageProductNotNull(product)).into(binding.ivProduct)
        binding.mtvTitle.text = product.title
        binding.mtvPrice.text = product.price.formatAsPrice()
        binding.mtvTimestamp.text = RelativeTime.getTimeAgo(product.timestamp, view.context)

        binding.root.setOnClickListener {
            val bundle = product.loadBundle()

            context.openActivity<ProductDetailActivity> {
                putExtra(Constant.BUNDLE_PRODUCT, bundle)
            }

        }

        binding.ivLike.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                //Creamos un like y consultamos en la base de dato si ese like existe

            }
        }
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