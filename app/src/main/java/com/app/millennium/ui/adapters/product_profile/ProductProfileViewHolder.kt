package com.app.millennium.ui.adapters.product_profile

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.app.millennium.R
import com.app.millennium.core.common.*
import com.app.millennium.core.utils.RelativeTime
import com.app.millennium.data.model.Product
import com.app.millennium.databinding.ItemListProductProfileBinding
import com.app.millennium.databinding.ViewBottomSheetConfirmDeleteProductBinding
import com.app.millennium.domain.use_case.product_db.DeleteProductUseCase
import com.app.millennium.domain.use_case.user_db.GetUserUseCase
import com.app.millennium.domain.use_case.user_db.UpdateUploadedProductsUserUseCase
import com.app.millennium.ui.activities.product_detail.ProductDetailActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductProfileViewHolder(
    private val view : View,
    private val context: Context
) : RecyclerView.ViewHolder(view), ProductProfileUsesCases{

    //Binding
    private val binding: ItemListProductProfileBinding = ItemListProductProfileBinding.bind(view)

    //Casos de uso
    override val deleteProductUseCase: DeleteProductUseCase
        get() = DeleteProductUseCase()
    override val getUserUseCase: GetUserUseCase
        get() = GetUserUseCase()
    override val updateUploadedProductsUserUseCase: UpdateUploadedProductsUserUseCase
        get() = UpdateUploadedProductsUserUseCase()

    //Producto
    private lateinit var product: Product

    //Cargar el producto
    fun loadData(product: Product){
        this.product = product
        initUI()
    }

    //inicializar la vista
    private fun initUI() {
        configDataProduct()
        configEventsOnClick()
    }

    /**
     * Metodo que setea los datos del producto
     */
    private fun configDataProduct() {
        //Seteamos todos los campos en la vista
        Picasso.get().load(getFirtsImageProductNotNull(product)).into(binding.ivProduct)
        binding.mtvTitle.text = product.title
        binding.mtvPrice.text = product.price.formatAsPrice()
        binding.mtvTimestamp.text = RelativeTime.getTimeAgo(product.timestamp, view.context)
    }

    /**
     * Metodo que registra todos los eventos de la vista del producto
     */
    private fun configEventsOnClick() {

        binding.root.setOnClickListener {
            val bundle = product.loadBundle()

            context.openActivity<ProductDetailActivity> {
                putExtra(Constant.BUNDLE_PRODUCT, bundle)
            }
        }

        binding.ivLikeProduct.setOnClickListener {
            Toast.makeText(context, "Like", Toast.LENGTH_SHORT).show()
        }

        binding.ivDeleteProduct.setOnClickListener {

            openBottomSheetConfirmDeleteProduct()
        }
    }

    /**
     * Metodo que construye un bottom sheet con la vista de confirmar la eliminacion
     * y devuelve un valor dependiendo de lo que haya pulsado el usuario si en eliminar
     * o cancelar
     */
    private fun openBottomSheetConfirmDeleteProduct() {

        //Creamos el bottom sheet dialog con el estilo qeu predefinimos
        //para los bottom sheet dialog
        val bottomSheetConfirmDeleteProduct =
            BottomSheetDialog(context, R.style.BottomSheetTheme)
        //Obtenemos la vista del layout bottomsheetDialog y la bindeamos
        var bindingBottomSheetDialog: ViewBottomSheetConfirmDeleteProductBinding? =
            ViewBottomSheetConfirmDeleteProductBinding.inflate(
                LayoutInflater.from(context)
            )

        //Le seteamos la vista al BottomSheetDialog creado
        bottomSheetConfirmDeleteProduct.setContentView(
            bindingBottomSheetDialog!!.root
        )

        //Y lo mostramos
        bottomSheetConfirmDeleteProduct.show()

        //Seteamos los metodos setOnClicListener a las textviews
        //de la vista del bottom sheet dialog
        bindingBottomSheetDialog.apply {
            mtvCancel.setOnClickListener {
                bottomSheetConfirmDeleteProduct.dismiss()
                bindingBottomSheetDialog = null
            }
            mtvDelete.setOnClickListener {
                bottomSheetConfirmDeleteProduct.dismiss()
                bindingBottomSheetDialog = null

                deleteProduct()
            }
        }

    }

    /**
     * Metodo que elimina un producto de la lista y cambia el valor uploadedProducts del usuario
     */
    private fun deleteProduct() {
        CoroutineScope(Dispatchers.IO).launch {
            //Eliminamos el producto
            deleteProductUseCase.invoke(product.id)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        //Si la tarea fue exitosa entonces modificamos el campo de uploadedProducts
                        //del usuario
                        CoroutineScope(Dispatchers.IO).launch {
                            product.idUser?.let { idUser ->
                                getUserUseCase.invoke(idUser)
                                    .addOnSuccessListener { _user ->
                                        val user = _user.data.convertUser()
                                        user.uploadedProducts -= 1
                                        //Y ahora actualizamos este campo
                                        CoroutineScope(Dispatchers.IO).launch {
                                            updateUploadedProductsUserUseCase.invoke(user)
                                                .addOnCompleteListener { task2 ->
                                                    if (task2.isSuccessful){
                                                        Toast.makeText(context, "Eliminado", Toast.LENGTH_SHORT).show()
                                                    }
                                                }
                                                .addOnFailureListener { exc ->
                                                    Toast.makeText(context, "${exc.message}", Toast.LENGTH_LONG).show()
                                                }
                                        }
                                    }
                                    .addOnFailureListener { exc ->
                                        Toast.makeText(context, "${exc.message}", Toast.LENGTH_LONG).show()
                                    }
                            }
                        }
                    }
                }
                .addOnFailureListener { exc ->
                    Toast.makeText(context, "${exc.message}", Toast.LENGTH_LONG).show()
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