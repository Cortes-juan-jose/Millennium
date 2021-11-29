package com.app.millennium.ui.activities.product_detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.millennium.R
import com.app.millennium.core.common.*
import com.app.millennium.core.utils.ConfigThemeApp
import com.app.millennium.core.utils.RelativeTime
import com.app.millennium.data.model.Like
import com.app.millennium.data.model.User
import com.app.millennium.databinding.ActivityProductDetailBinding
import com.app.millennium.ui.activities.profile_user_to_product.ProfileUserToProductActivity
import com.squareup.picasso.Picasso
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import java.util.*

class ProductDetailActivity : AppCompatActivity() {

    //Binding
    private lateinit var binding: ActivityProductDetailBinding
    private val viewModel: ProductDetailViewModel by viewModels()

    //Producto
    private lateinit var bundleProduct: Bundle

    //Usuario del producto
    private var user = User()

    //Id del usuario de la sesion
    private lateinit var idUserSession: String

    //Like
    private lateinit var like: Like
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bundleProduct = intent.getBundleExtra(Constant.BUNDLE_PRODUCT)!!
    
        initUI()
        initObservables()
    }

    /**
     * Inicializar la vista al completo
     */
    private fun initUI(){
        configToolbar()
        configComponents()
        configEventsClickButtons()
    }

    /**
     * Inicializar los observables
     */
    private fun initObservables() {
        viewModel.getUser.observe(
            this,
            {
                it?.let {
                    it.addOnSuccessListener { snapshot ->
                        user = snapshot?.toObject(User::class.java)!!
                        viewModel.getIdUserSession()
                    }
                }
            }
        )

        viewModel.getIdUserSession.observe(
            this,
            {
                it?.let {
                    idUserSession = it
                    binding.llUserData.visibility = View.VISIBLE
                    binding.llLoadDataUser.visibility = View.GONE
                    if (it != user.id){
                        binding.mbtnViewProfile.visibility = View.VISIBLE
                    }
                    configDataUser()
                    configDrawableLike()
                }
            }
        )

        viewModel.getLikeByProductByUserProductByUserSession.observe(
            this,
            {
                it?.let {
                    it.addOnFailureListener { exc -> Toast.makeText(this@ProductDetailActivity, "${exc.message}", Toast.LENGTH_SHORT).show() }

                    it.addOnSuccessListener { snapshot ->
                        snapshot?.let { _snapshot ->
                            if (!_snapshot.isEmpty){
                                binding.ivLike.setImageResource(R.drawable.ic_like_red)
                            } else {
                                binding.ivLike.setImageResource(R.drawable.ic_like)
                            }
                        }

                    }
                }
            }
        )

        viewModel.getLikeByProductByUserProductByUserSessionConfig.observe(
            this,
            {
                it?.let {
                    it.addOnFailureListener { exc -> Toast.makeText(this@ProductDetailActivity, "${exc.message}", Toast.LENGTH_SHORT).show() }

                    it.addOnSuccessListener { snapshot ->
                        snapshot?.let { _snapshot ->
                            if (!_snapshot.isEmpty){
                                //Si no es vacia significa que ya fue like entonces quitamos el like
                                viewModel.deleteLike(_snapshot.documents[0].id)
                                binding.ivLike.setImageResource(R.drawable.ic_like)
                            } else {
                                viewModel.saveLike(like)
                                binding.ivLike.setImageResource(R.drawable.ic_like_red)
                            }
                        }

                    }
                }
            }
        )
    }

    /**
     * Metodo que controla todos los eventos click del activity
     */
    private fun configEventsClickButtons() {

        var bundleUser = Bundle()

        binding.apply {
            ivBack.setOnClickListener { finish() }

            ivLike.setOnClickListener {
                //Configuramos la funcionalidad del like
                viewModel.getLikeByProductByUserProductByUserSessionConfig(like)
            }

            mbtnViewProfile.setOnClickListener {
                if (user.isNotNull()){
                    bundleUser = user.loadBundle()
                } else {
                    toast(getString(R.string.msg_info_tiempo_espera))
                }

                openActivity<ProfileUserToProductActivity> {
                    putExtra(Constant.BUNDLE_USER, bundleUser)
                }
            }
        }
    }

    /**
     * Metodo que configura todos los componentes del activity
     */
    private fun configComponents() {
        configSlider()
        configDataProduct()
        //Obtenemos el usuario de la publicacion
        viewModel.getUser(bundleProduct[Constant.PROP_ID_USER_PRODUCT].toString())
    }

    private fun configDrawableLike() {
        //Creamos un like con los datos
        like = Like(
            idUserToSession = idUserSession,
            idUserToPostProduct = user.id,
            idProduct = bundleProduct[Constant.PROP_ID_PRODUCT].toString(),
            timestamp = Date().time
        )

        viewModel.getLikeByProductByUserProductByUserSession(like)
    }

    /**
     * Metodo que setea toda la informacion del usuario
     */
    private fun configDataUser() {
        user.imgProfile?.let { Picasso.get().load(it).into(binding.civImagenPerfil) }
        binding.mtvNameUser.text = user.name
        binding.mtvEmailUser.text = user.email
    }

    /**
     * metodo que setea toda la informacion del producto
     */
    private fun configDataProduct() {
        binding.apply {
            mtvPrice.text = bundleProduct[Constant.PROP_PRICE_PRODUCT].toString().toDouble().formatAsPrice()
            mtvTimestamp.text = RelativeTime.getTimeAgo(bundleProduct[Constant.PROP_TIMESTAMP_PRODUCT].toString().toLong(), this@ProductDetailActivity)
            mtvTitle.text = bundleProduct[Constant.PROP_TITLE_PRODUCT].toString()
            mtvCategory.text = bundleProduct[Constant.PROP_CATEGORY_PRODUCT].toString()
            mtvDescription.text = bundleProduct[Constant.PROP_DESCRIPTION_PRODUCT].toString()

            bundleProduct[Constant.PROP_DESCRIPTION_PRODUCT]?.let {
                mtvDescription.text = (it as String)
            }

            bundleProduct[Constant.PROP_NEGOTIABLE_PRODUCT]?.let {
                mtvNegotiable.text = (it as String)
            }

            bundleProduct[Constant.PROP_PRODUCT_STATUS_PRODUCT]?.let {
                mtvStatusProduct.text = (it as String)
            }
        }
    }

    /**
     * Metodo que configura el slider de imagenes del producto
     */
    private fun configSlider() {
        val images = mutableListOf<CarouselItem>()
        bundleProduct[Constant.PROP_IMAGE1_PRODUCT]?.let {
            images.add(CarouselItem((it as String)))
        }
        bundleProduct[Constant.PROP_IMAGE2_PRODUCT]?.let {
            images.add(CarouselItem((it as String)))
        }
        bundleProduct[Constant.PROP_IMAGE3_PRODUCT]?.let {
            images.add(CarouselItem((it as String)))
        }
        bundleProduct[Constant.PROP_IMAGE4_PRODUCT]?.let {
            images.add(CarouselItem((it as String)))
        }

        binding.imCarousel.setData(images)
    }

    /**
     * Metodo que configura el toolbar
     */
    private fun configToolbar() {
        //Setear el color del toolbar dependiendo del tema del teléfono
        if (ConfigThemeApp.isThemeLight(this))
            binding.ctlAppbar.contentScrim = ContextCompat.getDrawable(this, R.drawable.toolbar_light)
        else
            binding.ctlAppbar.contentScrim = ContextCompat.getDrawable(this, R.drawable.toolbar_dark)

        (this as AppCompatActivity).setSupportActionBar(binding.toolbarDetailProduct)
        (this as AppCompatActivity).supportActionBar!!.title = ""
    }
}