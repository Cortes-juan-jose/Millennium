package com.app.millennium.ui.activities.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.app.millennium.R
import com.app.millennium.core.common.toast
import com.app.millennium.data.model.FCMBody
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
                            //Ahora creamos el token
                            //viewModel.createToken(idUser, tokenToDevice)

                            //ESTO ES UNA PRUEBA BORRAR
                            val data = mapOf(
                                "title" to "Funciona",
                                "body" to "Perfecto"
                            )
                            val fcmBody = FCMBody(
                                tokenToDevice, "high", "4500s", data
                            )
                            viewModel.sendNotification(fcmBody)
                        }
                    }
                }
            }
        )

        //BORRAR LUEGO
        viewModel.sendNotification.observe(
            this,
            {
                if (it.success == 1){
                    toast("Se ha enviado correctamente")
                }
            }
        )

        //crear token del usuario de la sesion
        viewModel.createToken.observe(
            this,
            {
                it?.let {
                    it.addOnFailureListener { exc -> toast("${exc.message}") }
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
}