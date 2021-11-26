package com.app.millennium.ui.fragments.profile

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.app.millennium.R
import com.app.millennium.core.common.*
import com.app.millennium.core.utils.ConfigThemeApp
import com.app.millennium.data.model.User
import com.app.millennium.databinding.FragmentProfileBinding
import com.app.millennium.ui.activities.edit_profile.EditProfileActivity
import com.app.millennium.ui.activities.info_app.InfoAppActivity
import com.app.millennium.ui.activities.login.LoginActivity
import com.app.millennium.ui.adapters.view_pager_profile.ViewPagerProfileAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    //Binding
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    //Viewmodel
    private val viewModel: ProfileViewModel by viewModels()

    //Usuario
    private var user: User? = null

    /**
     * Bindear la vista
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    /*
     * Una vez la vista creada inicializamos todos los componentes
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initObservables()
    }

    /**
     * Inicializar todos los componentes de la vista
     */
    private fun initUI() {
        viewModel.getIdUser() //Obtener el id del usuario que tiene iniciada la sesion
        configToolbar() //Configurar el toolbar
        configEditProfileButton() //Configurar el editar perfil
        configTabLayout() //Configurar el tab layout
    }

    /**
     * Metodo que configura los observers asociados a esta vista con el viewmodel
     */
    private fun initObservables() {
        //Observer para el id del usuario
        viewModel.getIdUser.observe(
            viewLifecycleOwner,
            {
                it?.let { viewModel.getUser(it) }
            }
        )
        //Observer para el usuario
        viewModel.getUser.observe(
            viewLifecycleOwner,
            { task ->
                task.addSnapshotListener { value, error ->
                    //Obtenemos la información en tiempo real
                    if (error!=null)
                        return@addSnapshotListener
                    //Si existe este usuario
                    if (value != null && value.exists()){
                        //Construimos un usuario a partir del map obtenido
                        viewModel.buildUser(value.data)
                    }
                }
            }
        )

        /**
         * Observer que construye un objeto user a partir de un map con las propiedades
         * del usuario obtenidas de la base de datos en tiempo real
         */
        viewModel.buildUser.observe(
            viewLifecycleOwner,
            {
                it?.let {
                    user = it
                    configComponents()
                }
            }
        )
    }

    /**
     * Metodo sobreescrito para cuando está vista se destruya
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Metodo para construir el menu del toolbar
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_profile_menu, menu)
    }

    /**
     * Metodo para dar funcionalidad al menu del toolbar
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.info_app -> { activity?.openActivity<InfoAppActivity> {  } }
            R.id.sign_out -> { signOut() }
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * Metodo que configura el toolbar
     */
    private fun configToolbar() {
        //Setear el color del toolbar dependiendo del tema del teléfono
        if (ConfigThemeApp.isThemeLight(requireContext()))
            binding.ctlAppbar.contentScrim = context?.let { ContextCompat.getDrawable(it, R.drawable.toolbar_light) }
        else
            binding.ctlAppbar.contentScrim = context?.let { ContextCompat.getDrawable(it, R.drawable.toolbar_dark) }

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarProfile)
        (activity as AppCompatActivity).supportActionBar!!.title = ""
        setHasOptionsMenu(true)
    }

    /**
     * Metodo que setea las propiedades del usuario
     * en sus campos correspondientes
     */
    private fun configComponents() {
        binding.apply {
            user?.let { user ->
                mtvUsername.text = user.name
                mtvEmail.text = user.email
                mtvUploadedProducts.text = user.uploadedProducts.toString()
                mtvOpinions.text = user.opinions.toString()

                if (user.imgCover.isNullOrEmpty()){
                    ivCover.scaleType = ImageView.ScaleType.CENTER_INSIDE
                    if (ConfigThemeApp.isThemeLight(requireContext())){
                        ivCover.setImageResource(R.drawable.ic_camera)
                    } else {
                        ivCover.setImageResource(R.drawable.ic_camera_dark)
                    }
                } else {
                    Picasso.get().load(user.imgCover).into(ivCover)
                    ivCover.scaleType = ImageView.ScaleType.CENTER_CROP
                }

                if (user.imgProfile.isNullOrEmpty()){
                    civImageProfile.setImageResource(R.drawable.ic_user_profile)
                } else {
                    Picasso.get().load(user.imgProfile).into(civImageProfile)
                }
            }
        }
    }

    /**
     * Metodo que configura el clicl del boton para editar perfil
     */
    private fun configEditProfileButton() {

        val bundle = Bundle()

        binding.btnEditProfile.setOnClickListener {

            if (user.isNotNull()){
                bundle.putString(Constant.PROP_ID_USER, user?.id)
                bundle.putString(Constant.PROP_IMG_COVER_USER, user?.imgCover)
                bundle.putString(Constant.PROP_IMG_PROFILE_USER, user?.imgProfile)
                bundle.putString(Constant.PROP_USERNAME_USER, user?.name)
                bundle.putString(Constant.PROP_PHONE_USER, user?.phone)
            } else {
                activity?.toast(getString(R.string.msg_info_tiempo_espera))
            }

            activity?.openActivity<EditProfileActivity> {
                putExtra(Constant.BUNDLE_USER, bundle)
            }
        }
    }

    /**
     * Metodo que configura todo el tabLayout para mostrar las opiniones y los productos
     */
    private fun configTabLayout() {

        val adapterViewPagerProfile =
            activity?.let {
                ViewPagerProfileAdapter(
                    it.supportFragmentManager,
                    it.lifecycle
                )
            }
        binding.vp2PostOpinions.adapter = adapterViewPagerProfile

        /*TabLayoutMediator(binding.tlPostOpinions, binding.vp2PostOpinions) {tab, pos ->
            when(pos){
                0 -> {tab.text=getString(R.string.tab_productos)}
                1 -> {tab.text=getString(R.string.tab_opiniones)}
            }
        }.attach()*/

        binding.tlPostOpinions.addTab(
            binding.tlPostOpinions.newTab().setText(getString(R.string.tab_productos))
        )
        binding.tlPostOpinions.addTab(
            binding.tlPostOpinions.newTab().setText(getString(R.string.tab_opiniones))
        )

        binding.tlPostOpinions.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    binding.vp2PostOpinions.currentItem = tab?.position!!
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            }
        )
        binding.vp2PostOpinions.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    binding.tlPostOpinions.selectTab(binding.tlPostOpinions.getTabAt(position))
                }
            }
        )
    }

    /**
     * Metodo para cerrar sesión
     */
    private fun signOut() {
        viewModel.signOut()
        activity?.toast(getString(R.string.msg_info_sesion_cerrada))
        activity?.openActivity<LoginActivity> {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }
}