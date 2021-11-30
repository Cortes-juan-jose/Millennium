package com.app.millennium.ui.activities.profile_user_to_product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.app.millennium.R
import com.app.millennium.core.common.*
import com.app.millennium.core.utils.ConfigThemeApp
import com.app.millennium.data.model.Chat
import com.app.millennium.databinding.ActivityProfileUserToProductBinding
import com.app.millennium.ui.activities.chat.ChatActivity
import com.app.millennium.ui.adapters.view_pager_profile.ViewPagerProfileAdapter
import com.app.millennium.ui.adapters.view_pager_profile_user_to_product_selected.ViewPagerProfileUserToProductSelectedAdapter
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import java.util.*

class ProfileUserToProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileUserToProductBinding
    private val viewModel: ProfileUserToProductViewModel by viewModels()

    private lateinit var bundle: Bundle

    private lateinit var idUserToSession: String
    private lateinit var chat: Chat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileUserToProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bundle = intent.getBundleExtra(Constant.BUNDLE_USER)!!

        initUI()
        initObservables()
    }

    private fun initUI(){
        configToolbar() // Toolbar
        configTabLayout() //Tablayout de los productos y las opiniones
        configSetDataUser() //Setear toda la informacion del usuario
        configEventsOnClick() //Eventos click del activity
    }

    private fun initObservables() {
        viewModel.getIdUserToSession.observe(
            this,
            {
                it?.let { id -> idUserToSession = id }
                //Creamos un chat
                chat = Chat(
                    id = idUserToSession + bundle[Constant.PROP_ID_USER].toString(),
                    idUserToSession = idUserToSession,
                    idUserToChat = bundle[Constant.PROP_ID_USER].toString(),
                    idsUsers = arrayListOf<String>(
                        idUserToSession,
                        bundle[Constant.PROP_ID_USER].toString()
                    ),
                    isWriting = false,
                    timestamp = Date().time
                )
                //Lo guardamos en un bundle
                val bundleChat = chat.loadBundle()
                //Y se lo pasamos al activity
                openActivity<ChatActivity> {
                    putExtra(Constant.BUNDLE_CHAT, bundleChat)
                }
            }
        )
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

        (this as AppCompatActivity).setSupportActionBar(binding.toolbarProfileToUser)
        (this as AppCompatActivity).supportActionBar!!.title = ""
    }

    /**
     * Metodo que setea las propiedades del usuario
     * en sus campos correspondientes
     */
    private fun configSetDataUser() {
        configImages()
        binding.mtvUsername.text = bundle[Constant.PROP_USERNAME_USER].toString()
        binding.mtvUploadedProducts.text = bundle[Constant.PROP_UPLOADED_PRODUCTS_USER].toString()
        binding.mtvOpinions.text = bundle[Constant.PROP_OPINIONS_USER].toString()
        binding.mtvEmail.text = bundle[Constant.PROP_EMAIL_USER].toString()
    }

    /**
     * Metodo que configura las imagenes del perfil del usuario del producto
     */
    private fun configImages() {
        if (bundle[Constant.PROP_IMG_COVER_USER].isNotNull()){
            Picasso.get().load(bundle[Constant.PROP_IMG_COVER_USER].toString()).into(binding.ivCover)
            binding.ivCover.scaleType = ImageView.ScaleType.CENTER_CROP
        } else {
            binding.ivCover.scaleType = ImageView.ScaleType.CENTER_INSIDE
            if (ConfigThemeApp.isThemeLight(this)){
                binding.ivCover.setImageResource(R.drawable.ic_camera)
            } else {
                binding.ivCover.setImageResource(R.drawable.ic_camera_dark)
            }
        }

        if (bundle[Constant.PROP_IMG_PROFILE_USER].isNotNull()){
            Picasso.get().load(bundle[Constant.PROP_IMG_PROFILE_USER].toString()).into(binding.civImageProfile)
        } else {
            binding.civImageProfile.setImageResource(R.drawable.ic_user_profile)
        }
    }

    /**
     * Metodo que configura todo el tabLayout para mostrar las opiniones y los productos
     */
    private fun configTabLayout() {

        val adapterViewPagerProfile = ViewPagerProfileUserToProductSelectedAdapter(
            supportFragmentManager,
            lifecycle,
            bundle[Constant.PROP_ID_USER].toString()
        )

        binding.vp2ProductsOpinions.adapter = adapterViewPagerProfile

        binding.tlProductsOpinions.addTab(
            binding.tlProductsOpinions.newTab().setText(getString(R.string.tab_productos))
        )
        binding.tlProductsOpinions.addTab(
            binding.tlProductsOpinions.newTab().setText(getString(R.string.tab_opiniones))
        )

        binding.tlProductsOpinions.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    binding.vp2ProductsOpinions.currentItem = tab?.position!!
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            }
        )
        binding.vp2ProductsOpinions.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    binding.tlProductsOpinions.selectTab(binding.tlProductsOpinions.getTabAt(position))
                }
            }
        )
    }

    /**
     * Metodo qeu registra todos los eventos del activity
     */
    private fun configEventsOnClick() {
        binding.ivBack.setOnClickListener { finish() }

        binding.mbtnChat.setOnClickListener {
            viewModel.getIdUserToSession()
        }
    }
}