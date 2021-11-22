package com.app.millennium.ui.fragments.profile

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.millennium.R
import com.app.millennium.core.common.openActivity
import com.app.millennium.core.common.toast
import com.app.millennium.core.utils.ConfigThemeApp
import com.app.millennium.data.model.User
import com.app.millennium.databinding.FragmentProfileBinding
import com.app.millennium.ui.activities.login.LoginActivity
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initObservables()
    }

    private fun initUI() {
        viewModel.getIdUser()
        configToolbar() //Configurar el toolbar
    }

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
                task?.let { _task ->
                    _task.addOnSuccessListener { document ->
                        document?.let { _document ->
                            if (_document.exists()){
                                //Obtenemos el usuario
                                user = _document.toObject(User::class.java)!!
                                configComponent()
                            } else {
                                activity?.toast(getString(R.string.msg_error_usuario_no_existe))
                            }
                        }
                    }
                }
            }
        )
    }

    /**
     * Metodo que setea las propiedades del usuario
     * en sus campos correspondientes
     */
    private fun configComponent() {
        binding.apply {
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
            R.id.info_app -> { activity?.toast("Abrir info")/*activity?.openActivity<PostProductActivity> {  }*/ }
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
     * Metodo para cerrar sesión
     */
    private fun signOut() {
        viewModel.signOut()
        activity?.openActivity<LoginActivity> {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }
}