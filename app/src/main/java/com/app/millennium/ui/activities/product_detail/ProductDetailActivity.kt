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
import com.app.millennium.data.model.User
import com.app.millennium.databinding.ActivityProductDetailBinding
import com.app.millennium.ui.activities.profile_user_to_product.ProfileUserToProductActivity
import com.squareup.picasso.Picasso
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import java.util.*

class ProductDetailActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityProductDetailBinding
    private val viewModel: ProductDetailViewModel by viewModels()

    private lateinit var bundleProduct: Bundle

    private var user = User()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bundleProduct = intent.getBundleExtra(Constant.BUNDLE_PRODUCT)!!
    
        initUI()
        initObservables()
    }

    private fun initUI(){
        configToolbar()
        configComponents()
        configEventsClickButtons()
    }

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
                    binding.llUserData.visibility = View.VISIBLE
                    binding.llLoadDataUser.visibility = View.GONE
                    if (it != user.id){
                        binding.mbtnViewProfile.visibility = View.VISIBLE
                    }
                    configDataUser()
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
                                if (ConfigThemeApp.isThemeLight(this))
                                    binding.ivLike.setImageResource(R.drawable.ic_like_dark)
                                else
                                    binding.ivLike.setImageResource(R.drawable.ic_like)
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
                //Creamos un like y consultamos en la base de dato si ese like existe
                /*val like = Like(
                    idUserToSession = idUser,
                    idUserToPostProduct = bundleProduct[Constant.PROP_ID_USER_PRODUCT].toString(),
                    idProduct = bundleProduct[Constant.PROP_ID_PRODUCT].toString(),
                    timestamp = Date().time
                )
                configLike(like)*/
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