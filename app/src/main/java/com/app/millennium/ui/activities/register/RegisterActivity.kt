package com.app.millennium.ui.activities.register

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.millennium.R
import com.app.millennium.core.common.*
import com.app.millennium.data.model.User
import com.app.millennium.databinding.ActivityRegisterBinding
import com.app.millennium.ui.activities.home.HomeActivity
import dmax.dialog.SpotsDialog
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel : RegisterViewModel by viewModels()

    private lateinit var dialogLoading: AlertDialog
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initObservables()
    }

    private fun initUI(){

        dialogLoading = SpotsDialog
            .Builder()
            .setContext(this)
            .setCancelable(false)
            .build()

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
                        tietUser.text.toString().trim(),
                        tietEmail.text.toString().trim(),
                        tietPhone.text.toString().trim(),
                        tietPassword.text.toString().trim(),
                        tietConfirmarPassword.text.toString().trim()
                    )
                ){
                    dialogLoading.setMessage(getString(R.string.msg_alert_creando_cuenta))
                    dialogLoading.show()
                    //En el caso que los campos sean correctos se creara una cuenta y un usuairo
                    viewModel.createAccount(
                        tietEmail.text.toString(),
                        tietConfirmarPassword.text.toString()
                    )
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
        var usernameValid = false
        var emailValid = false
        var phoneValid = false
        var passwordValid = false
        var passwordConfirmValid = false


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
        /**
         * Observable para crear una cuenta en firebase
         */
        viewModel.createAccount.observe(
            this,
            { task ->
                task.addOnCompleteListener {
                    if (it.isSuccessful){
                        /**
                         * si la cuenta se ha creado correctamente
                         * entonces obtenemos el id de la misma para
                         * poder guardar el usuario en la db Collection users
                         * correctamente
                         */
                        viewModel.getId()

                    }
                }
                task.addOnFailureListener {
                    dialogLoading.dismiss()
                    toast("${it.message}")
                }
            }
        )

        /**
         * Observable para iniciar sesion al terminar de crear una cuenta
         */
        viewModel.signInWithEmailAndPassword.observe(
            this,
            { task ->
                task.addOnCompleteListener {
                    dialogLoading.dismiss()
                    if (it.isSuccessful){
                        /**
                         * Si el inicio de sesion se lleva a cabo entonces abrimos el homeAct
                         */
                        openActivity<HomeActivity> {
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                    }
                }
                task.addOnFailureListener {
                    dialogLoading.dismiss()
                    toast("${it.message}")
                }
            }
        )

        /**
         * Observable para obtener el id del usuario de inicio de sesion
         * para guardarlo en la base de datos de los usuarios
         */
        viewModel.getId.observe(
            this,
            {
                it?.let {
                    /**
                     * Obtenemos el ID y creamos un usuario
                     */
                    binding.apply {
                        user = User(
                            id = it,
                            name = tietUser.text.toString(),
                            email = tietEmail.text.toString(),
                            phone = tietPhone.text.toString(),
                            timestamp = Date().time
                        )
                        /**
                         * Luego guardamos este usuario en la db Collection users
                         */
                        viewModel.saveUser(user)
                    }
                }
            }
        )

        /**
         * Observable para guardar un usuario en la db Collection users
         */
        viewModel.saveUser.observe(
            this,
            { task ->
                task?.let { task_let ->
                    task_let.addOnCompleteListener {
                        if (it.isSuccessful) {
                            /**
                             * Si se guarda correctamente se inicia sesión
                             */
                            dialogLoading.setMessage(getString(R.string.msg_alert_iniciando_sesion))
                            viewModel.signInWithEmailAndPassword(
                                binding.tietEmail.text.toString(),
                                binding.tietConfirmarPassword.text.toString()
                            )
                        }
                    }

                    task_let.addOnFailureListener {
                        dialogLoading.dismiss()
                        toast("${it.message}")
                    }
                }
            }
        )
    }
}