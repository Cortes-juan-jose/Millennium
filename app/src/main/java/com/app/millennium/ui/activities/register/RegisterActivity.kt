package com.app.millennium.ui.activities.register

import android.os.Bundle
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
                //Primero quitamos el foco y cerramos el teclado de esta vista
                this@RegisterActivity.reload()

                /**
                 * Validamos todos los campos para registrarse
                 */
                if (validarInputs(
                        tietUser.text.toString(),
                        tietEmail.text.toString(),
                        tietPhone.text.toString(),
                        tietPassword.text.toString(),
                        tietConfirmarPassword.text.toString()
                    )
                ){
                    //En el caso que los campos sean correctos se creara una cuenta y un usuairo
                    /*viewModel.createAccount(
                        tietEmail.text.toString(),
                        tietConfirmarPassword.text.toString()
                    )*/
                    toast("Usuario creado")
                } else {
                    toast("Datos incorrectos")
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
        var usernameValid: Boolean = false
        var emailValid: Boolean = false
        var phoneValid: Boolean = false
        var passwordValid: Boolean = false
        var passwordConfirmValid: Boolean = false


        binding.apply {

            //Validando el username
            if (username.isUsername()){
                usernameValid = true
                tilUser.removeError()
            } else {
                tietUser.setText("")
                tilUser.applyError(getString(R.string.msg_error_username))
            }

            //Validando el email
            if (email.isEmail()){
                emailValid = true
                tilEmail.removeError()
            } else {
                tietEmail.setText("")
                tilEmail.applyError(getString(R.string.msg_error_email))
            }

            //Validando el telefono
            //Solo se validará si se ha introducido dígitos
            if (phone.isNotEmpty()){
                if (phone.isPhone()){
                    phoneValid = true
                    tilPhone.removeError()
                } else {
                    tietPhone.setText("")
                    tilPhone.applyError(getString(R.string.msg_error_phone))
                }
            } else{
                phoneValid = true
                tilPhone.removeError()
            }

            //Validando la contraseña
            if (password.isPassword()){
                passwordValid = true
                tilPassword.removeError()
            } else {
                tietPassword.setText("")
                tilPassword.applyError(getString(R.string.msg_error_password))
            }

            //Validando la confirmacion de la contraseña
            if (confirmPassword.isNotEmpty() &&
                confirmPassword == password
            ){
                passwordConfirmValid = true
                tilConfirmarPassword.removeError()
            } else {
                tietConfirmarPassword.setText("")
                tilConfirmarPassword.applyError(getString(R.string.msg_error_password_confirm))
            }
        }

        return usernameValid &&
                emailValid &&
                phoneValid &&
                passwordValid &&
                passwordConfirmValid
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