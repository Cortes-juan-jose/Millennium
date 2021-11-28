package com.app.millennium.ui.activities.product_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.app.millennium.R
import com.app.millennium.core.common.*
import com.app.millennium.core.utils.ConfigThemeApp
import com.app.millennium.core.utils.RelativeTime
import com.app.millennium.data.model.User
import com.app.millennium.databinding.ActivityProductDetailBinding
import com.app.millennium.ui.activities.edit_profile.EditProfileActivity
import com.app.millennium.ui.activities.profile_user_to_product.ProfileUserToProductActivity
import com.squareup.picasso.Picasso
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class ProductDetailActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityProductDetailBinding
    private val viewModel: ProductDetailViewModel by viewModels()

    private lateinit var bundle: Bundle

    private var user = User()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bundle = intent.getBundleExtra(Constant.BUNDLE_PRODUCT)!!
    
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
                        binding.llUserData.visibility = View.VISIBLE
                        binding.llLoadDataUser.visibility = View.GONE
                        configDataUser()
                    }
                }
            }
        )
    }

    /**
     * Metodo que controla todos los eventos click del activity
     */
    private fun configEventsClickButtons() {

        val bundleUser = Bundle()

        binding.apply {
            ivBack.setOnClickListener { finish() }

            ivLike.setOnClickListener { toast("Like") }

            mbtnViewProfile.setOnClickListener {
                if (user.isNotNull()){
                    bundleUser.putString(Constant.PROP_ID_USER, user.id)
                    bundleUser.putString(Constant.PROP_USERNAME_USER, user.name)
                    bundleUser.putString(Constant.PROP_EMAIL_USER, user.email)
                    bundleUser.putString(Constant.PROP_IMG_COVER_USER, user.imgCover)
                    bundleUser.putString(Constant.PROP_IMG_PROFILE_USER, user.imgProfile)
                    bundleUser.putInt(Constant.PROP_UPLOADED_PRODUCTS_USER, user.uploadedProducts)
                    bundleUser.putInt(Constant.PROP_OPINIONS_USER, user.opinions)
                } else {
                    toast(getString(R.string.msg_info_tiempo_espera))
                }

                openActivity<ProfileUserToProductActivity> {
                    putExtra(Constant.BUNDLE_USER, bundleUser)
                }
            }
        }
    }

    private fun configComponents() {
        configSlider()
        configDataProduct()
        //Obtenemos el usuario de la publicacion
        viewModel.getUser(bundle[Constant.PROP_ID_USER_PRODUCT].toString())
    }

    private fun configDataUser() {
        user.imgProfile?.let { Picasso.get().load(it).into(binding.civImagenPerfil) }
        binding.mtvNameUser.text = user.name
        binding.mtvEmailUser.text = user.email
    }

    private fun configDataProduct() {
        binding.apply {
            mtvPrice.text = bundle[Constant.PROP_PRICE_PRODUCT].toString().toDouble().formatAsPrice()
            mtvTimestamp.text = RelativeTime.getTimeAgo(bundle[Constant.PROP_TIMESTAMP_PRODUCT].toString().toLong(), this@ProductDetailActivity)
            mtvTitle.text = bundle[Constant.PROP_TITLE_PRODUCT].toString()
            mtvCategory.text = bundle[Constant.PROP_CATEGORY_PRODUCT].toString()
            mtvDescription.text = bundle[Constant.PROP_DESCRIPTION_PRODUCT].toString()

            bundle[Constant.PROP_DESCRIPTION_PRODUCT]?.let {
                mtvDescription.text = (it as String)
            }

            bundle[Constant.PROP_NEGOTIABLE_PRODUCT]?.let {
                mtvNegotiable.text = (it as String)
            }

            bundle[Constant.PROP_PRODUCT_STATUS_PRODUCT]?.let {
                mtvStatusProduct.text = (it as String)
            }
        }
    }

    private fun configSlider() {
        val images = mutableListOf<CarouselItem>()
        bundle[Constant.PROP_IMAGE1_PRODUCT]?.let {
            images.add(CarouselItem((it as String)))
        }
        bundle[Constant.PROP_IMAGE2_PRODUCT]?.let {
            images.add(CarouselItem((it as String)))
        }
        bundle[Constant.PROP_IMAGE3_PRODUCT]?.let {
            images.add(CarouselItem((it as String)))
        }
        bundle[Constant.PROP_IMAGE4_PRODUCT]?.let {
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