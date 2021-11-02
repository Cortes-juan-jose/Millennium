package com.app.millennium.ui.activities.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.app.millennium.core.common.isNull
import com.app.millennium.core.common.openActivity
import com.app.millennium.ui.activities.home.HomeActivity
import com.app.millennium.ui.activities.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUI()
    }
    
    private fun initUI(){
        initObservables()
        viewModel.getCurrentSession()
    }

    private fun initObservables() {
        /**
         * Observable para obtener el inicio
         * de sesion del usuario que se haya
         * logeado en la app y tenga una sesion
         * iniciada
         */
        viewModel.getCurrentSessionLiveData.observe(
            this,
            {
                if (it.isNull()){
                    openActivity<LoginActivity> {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                } else {
                    openActivity<HomeActivity> {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                }
            }
        )
    }
}