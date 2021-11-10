package com.app.millennium.ui.activities.register

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.millennium.R
import com.app.millennium.core.common.*
import com.app.millennium.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel : RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initObservables()
    }

    private fun initUI(){
        binding.apply {
            ivBack.setOnClickListener {
                finish()
            }
            mbtnRegister.setOnClickListener {
                /**
                 * Validamos todos los campos para registrarse
                 */
                if (
                    validarInputs(
                        tietUser.text.toString(),
                        tietEmail.text.toString(),
                        tietPhone.text.toString(),
                        tietPassword.toString(),
                        tietConfirmarPassword.text.toString()
                    )
                ){
                    //En el caso que los campos sean correctos se creara una cuenta y un usuairo
                    viewModel.createAccount(
                        tietEmail.text.toString(),
                        tietConfirmarPassword.text.toString()
                    )
                }
            }
        }
    }

    /**
     * Metodo para validar los campos, devuelve true
     * si todos los campos son correctos y devuelve
     * false de todo lo contrario
     */
    private fun validarInputs(
        username: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        return false
    }

    private fun initObservables(){
        viewModel.createAccount.observe(
            this,
            { task ->
                task.addOnCompleteListener {
                    if (task.isSuccessful){
                        toast("Registrado")
                    }
                }
                task.addOnFailureListener {
                    toast("${it.message}")
                }
            }
        )
    }
}