package com.app.millennium.ui.activities.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.app.millennium.R
import com.app.millennium.core.common.toast
import com.app.millennium.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeActivityViewModel by viewModels()

    private lateinit var idUser: String
    private lateinit var tokenToDevice: String
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    
        initUI()
        initObservables()
    }

    private fun initObservables() {
        //Obtener el id del usuario de la sesion
        viewModel.getIdUserSession.observe(
            this,
            {
                it?.let {
                    idUser = it
                    //Ahora vamos a obtener el token del dispositivo
                    viewModel.getTokenToDevice()
                }
            }
        )

        //Obtener el token del dispositivo
        viewModel.getTokenToDevice.observe(
            this,
            {
                it?.let {
                    it.addOnSuccessListener { token ->
                        token?.let { _token ->
                            tokenToDevice = _token
                            //Ahora creamos el token pero primero miramos si este token
                            //ya existe para ello consultamos el token
                            viewModel.getToken(idUser)
                        }
                    }
                }
            }
        )

        //Obtener el token del usuario de la sesion
        viewModel.getToken.observe(
            this,
            {
                it?.let {
                    it.addOnSuccessListener { token ->
                        token?.let { _token ->
                            if (!_token.exists()){
                                //Si no existe lo creamos
                                viewModel.createToken(idUser, tokenToDevice)
                            }
                        }
                    }
                }
            }
        )

        //crear token del usuario de la sesion
        viewModel.createToken.observe(
            this,
            {
                it?.let {
                    it.addOnFailureListener { exc -> toast("${exc.message}") }
                    it.addOnCompleteListener {
                        toast("Completado")
                    }
                }
            }
        )
    }

    private fun initUI(){

        //Obtenemos el id del usuario
        viewModel.getIdUserSession()

        /**
         * El home activity se encargará se levar consigo la navegación entre los fragments del
         * bottom navigation view
         */
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bnvMenu.setupWithNavController(navController)
    }

    private fun configTokenToDevice() {

    }
}