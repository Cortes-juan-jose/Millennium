package com.app.millennium.ui.activities.post_product

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.millennium.core.common.openActivity
import com.app.millennium.core.common.reload
import com.app.millennium.core.common.toast
import com.app.millennium.databinding.ActivityPostProductBinding
import com.app.millennium.ui.activities.home.HomeActivity

class PostProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        openActivity<HomeActivity> {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }

    private fun initUI(){

        /**
         * Boton para ir hacia atras
         */
        binding.ivBack.setOnClickListener {onBackPressed()}

        /**
         * Seleccionar imagenes
         */
        configImages()

        /**
         * Campos selectores
         */
        configInputsSelectors()

        /**
         * Boton para publicar el producto
         */
        configPostProduct()
    }

    /**
     * Metodo para publicar un producto
     */
    private fun configPostProduct() {
        binding.btnPost.setOnClickListener {
            this@PostProductActivity.reload()
            validarInputs()
        }
    }

    /**
     * Metodo que valida todos los inputs
     */
    private fun validarInputs() {
        toast("Validando inputs")
    }

    /**
     * Metodo que configura los editext como botones de seleccion
     */
    private fun configInputsSelectors() {
        binding.apply {
            tietCategory.setOnClickListener { toast("Category") }
            tietNegotiable.setOnClickListener { toast("Negotiable") }
            tietProductStatus.setOnClickListener { toast("Product Status") }
        }
    }

    /**
     * Metodo que configura la selección de las imagenes
     */
    private fun configImages() {
        binding.apply {
            cvImgPost1.setOnClickListener { toast("IMG1") }
            cvImgPost2.setOnClickListener { toast("IMG2") }
            cvImgPost3.setOnClickListener { toast("IMG3") }
            cvImgPost4.setOnClickListener { toast("IMG4") }
        }
    }
}