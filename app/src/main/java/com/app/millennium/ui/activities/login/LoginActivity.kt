package com.app.millennium.ui.activities.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.app.millennium.core.common.openActivity
import com.app.millennium.core.common.toast
import com.app.millennium.databinding.ActivityLoginBinding
import com.app.millennium.ui.activities.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    
        initUI()
    }
    
    private fun initUI(){
        binding.apply { 
            mtvRegister.setOnClickListener { view ->
                Toast.makeText(this@LoginActivity, "pulsado", Toast.LENGTH_SHORT).show()
            }

            btnLoginGoogle.setOnClickListener { view ->
            }

            mbtnSingup.setOnClickListener { view ->
                if (!tietEmail.text.isNullOrEmpty() && !tietPassword.text.isNullOrEmpty()){
                    signInWithEmailAndPassword(tietEmail.text.toString(), tietPassword.text.toString())
                } else {
                    toast("Campos vacíos")
                }
            }
        }
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        viewModel.signInEmailPassword(email, password)
        viewModel.signInWithEmailAndPassword.observe(
            this@LoginActivity,
            { task ->
                task.addOnCompleteListener { authResult ->
                    if (authResult.isSuccessful){
                        openActivity<RegisterActivity> {  }
                    }
                }
                task.addOnFailureListener {
                    toast("Usuario no existe")
                }
            }
        )
    }
}