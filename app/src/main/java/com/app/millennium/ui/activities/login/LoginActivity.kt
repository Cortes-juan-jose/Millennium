package com.app.millennium.ui.activities.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.millennium.R
import com.app.millennium.core.common.isNull
import com.app.millennium.core.common.openActivity
import com.app.millennium.core.common.toast
import com.app.millennium.databinding.ActivityLoginBinding
import com.app.millennium.ui.activities.home.HomeActivity
import com.app.millennium.ui.activities.register.RegisterActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    /**
     * Objeto de configuracion de google
     */
    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(R.string.clave_client_web_id.toString())
        .requestEmail()
        .build()
    private lateinit var googleSignInClient : GoogleSignInClient

    /**
     * Launcher SignIn Google
     */
    private val launcherGoogleSignIn =
        registerForActivityResult(
            StartActivityForResult()
        ){
            it?.let {
                if (it.resultCode == RESULT_OK){
                    toast("Iniciado")
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initObservables()
    }

    private fun initUI() {

        binding.apply {

            /**
             * Boton para entrar en la aplicacion
             */
            mbtnSingIn.setOnClickListener {
                signInEmailPassword()
            }

            /**
             * Boton para logearse con google
             */
            btnSignInGoogle.setOnClickListener {
                signInGoogle()
            }

            /**
             * material text view para registrarse
             */
            mtvRegister.setOnClickListener {
                openActivity<RegisterActivity> { }
            }
        }
    }

    private fun signInEmailPassword() {

        binding.apply {
            /**
             * Si los campos del email y contraseña no son vacios entonces
             * se inicia sesin
             */
            if (!tietEmail.text.isNullOrEmpty() && !tietPassword.text.isNullOrEmpty()) {
                viewModel.signInEmailPassword(
                    tietEmail.text.toString(),
                    tietPassword.text.toString()
                )
            } else {
                /**
                 * De lo contrario, se mostrara un mensaje de error indicando
                 * que los campos no pueden estar vacios
                 */
                toast("Campos vacíos")
            }

        }
    }

    private fun signInGoogle() {

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val intentGoogle = googleSignInClient.signInIntent
        launcherGoogleSignIn.launch(intentGoogle)
    }

    /**
     * Metodo para inicializar todos los observadores del live data
     */
    private fun initObservables() {

        /**
         * Observable para obtener la tarea
         * que desempeña el inicio de sesion
         * con email y contraseña
         */
        viewModel.signInWithEmailAndPassword.observe(
            this@LoginActivity,
            { task ->
                task.addOnCompleteListener { authResult ->
                    //Si la tarea se completa entonces comprobamos si fue exitosa
                    if (authResult.isSuccessful) {
                        //si fue exitosa entonces se navegará hasta el HomeActivity
                        //Ahora esta de prueba con RegisterActivity
                        //openActivity es una funcion de extensio creada para
                        //navegar hacia activities con data y con flags
                        openActivity<HomeActivity> {
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                    }
                }
                task.addOnFailureListener { exc ->
                    //En el caso de que la tarea haya sido fallida entonces mostrará un mensaje
                    //de error indicando el problema
                    toast(exc.message.toString())
                }
            }
        )
    }
}