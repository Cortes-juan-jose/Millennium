package com.app.millennium.ui.activities.login

import android.content.Intent
import android.os.Bundle
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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    private lateinit var googleSignInClient: GoogleSignInClient

    /**
     * Launcher SignIn Google
     */
    private val launcherGoogleSignIn =
        registerForActivityResult(
            StartActivityForResult()
        ) {
            //Al lanzar el intent y elegir una cuenta de google o no, a la vuelta
            //debemos controlar esa respuesta
            //pues si esa respuesta es ok obtenemos la cuenta seleccionada
            //de lo contrario que no haga nada, pues el usuario a cerrado el intent.
            it?.let {
                if (it.resultCode == RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                    try {
                        //Al obtener la cuenta debemos autenticarla en firebase
                        //pasandole el idToken de google para obtener
                        //las credenciales, y poder iniciar sesión con google
                        val account = task.getResult(ApiException::class.java)!!
                        signInGoogle(account.idToken!!)
                    } catch (e: ApiException) {
                        toast("Google sign in failed")
                    }
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

    /**
     * Inicializacion de eventos de la ui
     */
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
                launcherSignInGoogle()
            }

            /**
             * material text view para registrarse
             */
            mtvRegister.setOnClickListener {
                openActivity<RegisterActivity> { }
            }
        }
    }

    /**
     * Metodo para construir y lanzar el intent del inicio de sesion con google
     */
    private fun launcherSignInGoogle() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.signOut()

        launcherGoogleSignIn.launch(googleSignInClient.signInIntent)
    }

    /**
     * Metodo para iniciar sesion con google
     */
    private fun signInGoogle(idToken: String) {
        viewModel.signInGoogle(idToken)
    }

    /**
     * Metodo para iniciar sesion con email y contraseña
     */
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
            this,
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

        /**
         * Observable para obtener la tarea
         * que desempeña el inicio de sesion
         * con google
         */
        viewModel.signInGoogle.observe(
            this,
            { task ->
                task.addOnCompleteListener { authResult ->
                    //Si la tarea se completa entonces comprobamos si fue exitosa
                    if (authResult.isSuccessful) {
                        //si fue exitosa entonces se navegará hasta el HomeActivity
                        //Ahora esta de prueba con RegisterActivity
                        //openActivity es una funcion de extensio creada para
                        //navegar hacia activities con data y con flags
                        val user = Firebase.auth.currentUser
                        toast("${user?.displayName}")
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