package com.app.millennium.ui.adapters.product_profile_user_to_product_selected

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.app.millennium.R
import com.app.millennium.core.common.*
import com.app.millennium.core.utils.ConfigThemeApp
import com.app.millennium.core.utils.RelativeTime
import com.app.millennium.data.model.Like
import com.app.millennium.data.model.Product
import com.app.millennium.databinding.ItemListProductProfileBinding
import com.app.millennium.databinding.ItemListProductProfileUserToProductSelectedBinding
import com.app.millennium.databinding.ViewBottomSheetConfirmDeleteProductBinding
import com.app.millennium.domain.use_case.likes_db.DeleteLikeUseCase
import com.app.millennium.domain.use_case.likes_db.GetAllLikeByUserUseCase
import com.app.millennium.domain.use_case.likes_db.GetLikeByProductByUserProductByUserSessionUseCase
import com.app.millennium.domain.use_case.likes_db.SaveLikeUseCase
import com.app.millennium.domain.use_case.product_db.DeleteProductUseCase
import com.app.millennium.domain.use_case.user_auth.GetIdUseCase
import com.app.millennium.domain.use_case.user_db.GetUserUseCase
import com.app.millennium.domain.use_case.user_db.UpdateUploadedProductsUserUseCase
import com.app.millennium.ui.activities.product_detail.ProductDetailActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ProductProfileUserToProductSelectedViewHolder(
    private val view : View,
    private val context: Context
) : RecyclerView.ViewHolder(view), ProductProfileUserToProductSelectedUsesCases{

    //Binding
    private val binding: ItemListProductProfileUserToProductSelectedBinding = ItemListProductProfileUserToProductSelectedBinding.bind(view)

    //Casos de uso
    override val saveLikeUseCase: SaveLikeUseCase
        get() = SaveLikeUseCase()
    override val getAllLikeByUserUseCase: GetAllLikeByUserUseCase
        get() = GetAllLikeByUserUseCase()
    override val getLikeByProductByUserProductByUserSessionUseCase: GetLikeByProductByUserProductByUserSessionUseCase
        get() = GetLikeByProductByUserProductByUserSessionUseCase()
    override val getIdUseCase: GetIdUseCase
        get() = GetIdUseCase()
    override val deleteLikeUseCase: DeleteLikeUseCase
        get() = DeleteLikeUseCase()

    //Producto
    private lateinit var product: Product

    //Id del usuario de la sesion
    private var idUser: String? = null

    //Cargar el producto
    fun loadData(product: Product){
        this.product = product
        initUI()
    }

    //inicializar la vista
    private fun initUI() {
        configDataProduct()
        configEventsOnClick()
        configDrawableLike()
    }

    private fun configDataProduct() {
        //Seteamos todos los campos en la vista
        Picasso.get().load(getFirtsImageProductNotNull(product)).into(binding.ivProduct)
        binding.mtvTitle.text = product.title
        binding.mtvPrice.text = product.price.formatAsPrice()
        binding.mtvTimestamp.text = RelativeTime.getTimeAgo(product.timestamp, view.context)
    }

    private fun configEventsOnClick() {
        //Evento para seleccionar un producto
        binding.root.setOnClickListener {
            val bundle = product.loadBundle()
            context.openActivity<ProductDetailActivity> {
                putExtra(Constant.BUNDLE_PRODUCT, bundle)
            }
        }

        //evento para dar like a un producto
        binding.ivLikeProduct.setOnClickListener {
            //Creamos un like y consultamos en la base de dato si ese like existe
            val like = Like(
                idUserToSession = idUser,
                idUserToPostProduct = product.idUser,
                idProduct = product.id,
                timestamp = Date().time
            )
            configLike(like)
        }
    }

    /**
     * Metodo que configura el drawable del like
     */
    private fun configDrawableLike() {
        CoroutineScope(Dispatchers.IO).launch {
            idUser = getIdUseCase.invoke()
            idUser?.let {
                getAllLikeByUserUseCase.invoke(it)
                    .addOnFailureListener { exc -> Toast.makeText(context, "${exc.message}", Toast.LENGTH_SHORT).show() }

                    .addOnSuccessListener { snapshot ->

                        snapshot?.let { _snapshot ->
                            if (!_snapshot.isEmpty){
                                for (document in _snapshot){
                                    if (document.exists()){
                                        if (document.getString(Constant.PROP_ID_PRODUCT_LIKE) == product.id){
                                            binding.ivLikeProduct.setImageResource(R.drawable.ic_like_red)
                                        }
                                    }
                                }
                            }
                        }
                    }
            }
        }
    }

    /**
     * Metodo que desarrolla la lógica del like
     * Si el like ya existe significa que el usuario lo quiere quitar de megusta
     * de lo contrario, si el like no existe el usuario quiere dar like al producto
     */
    private fun configLike(like: Like) {
        CoroutineScope(Dispatchers.IO).launch {

            getLikeByProductByUserProductByUserSessionUseCase.invoke(like)
                .addOnFailureListener { exc -> Toast.makeText(context, "${exc.message}", Toast.LENGTH_SHORT).show() }

                .addOnSuccessListener { snapshot ->
                    snapshot?.let { _snapshot ->
                        if (!_snapshot.isEmpty){
                            //Si no es vacia esta lista de likes significa que ya estaba asignada como me gusta
                            //quitamos el megusta y lo borramos en la base de datos
                            CoroutineScope(Dispatchers.IO).launch {
                                deleteLikeUseCase.invoke(_snapshot.documents[0].id)
                            }
                            if (ConfigThemeApp.isThemeLight(context))
                                binding.ivLikeProduct.setImageResource(R.drawable.ic_like_dark)
                            else
                                binding.ivLikeProduct.setImageResource(R.drawable.ic_like)
                        } else {
                            //de lo contrario significa que se va a dar me gusta por lo tanto
                            //se guarda en la base de datos y se pone en rojo
                            CoroutineScope(Dispatchers.IO).launch {
                                saveLikeUseCase.invoke(like)
                            }
                            binding.ivLikeProduct.setImageResource(R.drawable.ic_like_red)
                        }
                    }

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