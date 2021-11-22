package com.app.millennium.ui.fragments.profile

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.millennium.R
import com.app.millennium.core.common.openActivity
import com.app.millennium.core.common.toast
import com.app.millennium.core.utils.ConfigThemeApp
import com.app.millennium.databinding.FragmentProfileBinding
import com.app.millennium.ui.activities.login.LoginActivity

class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configToolbar() //Configurar el toolbar
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