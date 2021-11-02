package com.app.millennium.ui.activities.login

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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

    private fun initUI() {

        initObservables()

        //Comprobar (temporalmente) que el inicio de sesion es correct0
        viewModel.getCurrentSession()


        binding.apply {

            /**
             * material text view para registrarse
             */
            mtvRegister.setOnClickListener {
                Toast.makeText(this@LoginActivity, "pulsado", Toast.LENGTH_SHORT).show()
            }

            /**
             * Boton para logearse con google
             */
            btnLoginGoogle.setOnClickListener {
            }

            /**
             * Boton para entrar en la aplicacion
             */
            mbtnSingup.setOnClickListener {

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
                        openActivity<RegisterActivity> { }
                    }
                }
                task.addOnFailureListener { exc ->
                    //En el caso de que la tarea haya sido fallida entonces mostrará un mensaje
                    //de error indicando el problema
                    toast("Sin conexion a internet o usuario inexistente -> ${exc.message}")
                }
            }
        )


        /**
         * Observable para obtener el inicio
         * de sesion del usuario que se haya
         * logeado en la app y tenga una sesion
         * iniciada. TEMPORAL
         */
        viewModel.getCurrentSessionLiveData.observe(
            this,
            {
                it?.let { user ->
                    toast(user.uid)
                    toast(user.email.toString())
                }
            }
        )
    }
}