package com.app.millennium.ui.activities.post_product

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.millennium.core.common.Constant
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
        initObservables()
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

    private fun initObservables() {

    }

    /**
     * Metodo que configura la selección de las imagenes
     */
    private fun configImages() {
        /**
         * Primero le seteamos un tag a las imagenes
         */
        setTagDefaultImages()
        binding.apply {

            cvImgPost1.setOnClickListener {
                this@PostProductActivity.reload()
                configBottomSheetOption(Constant.RESULT_CODE_CV_IMG_POST_1)
            }
            cvImgPost2.setOnClickListener {
                this@PostProductActivity.reload()
                configBottomSheetOption(Constant.RESULT_CODE_CV_IMG_POST_2)
            }
            cvImgPost3.setOnClickListener {
                this@PostProductActivity.reload()
                configBottomSheetOption(Constant.RESULT_CODE_CV_IMG_POST_3)
            }
            cvImgPost4.setOnClickListener {
                this@PostProductActivity.reload()
                configBottomSheetOption(Constant.RESULT_CODE_CV_IMG_POST_4)
            }
        }
    }

    /**
     * Metodo para setear el tag a las imagenes para identificar
     * qué campos image viexs tiene una imagen establecida
     */
    private fun setTagDefaultImages() {
        binding.apply {
            ivImgPost1.tag = Constant.TAG_DEFAULT
            ivImgPost2.tag = Constant.TAG_DEFAULT
            ivImgPost3.tag = Constant.TAG_DEFAULT
            ivImgPost4.tag = Constant.TAG_DEFAULT
        }
    }

    /**
     * Metodo para abrir el bottomSheet de las acciones
     * para abrir la cámara o la galeria o si ya tiene una
     * imagen abrir el botttomSheet que da la accion
     * sobre eliminar la imagen o editarla
     */
    private fun configBottomSheetOption(resultCode: Int) {
        when (resultCode){
            /**
             * El requestCode se necesita para establecer luego la imagen capturada a un inputs
             * con el resultCode le aplicamos esta lógica para llevar el control de los inputs
             */
            Constant.RESULT_CODE_CV_IMG_POST_1 -> {
                //Ahora debemos comprobar si tiene una imagen o no tiene una imagen
                //Verificando que el tag sea default
                //Si es default significa que no tiene ninguna imagen
                if (binding.ivImgPost1.tag.equals(Constant.TAG_DEFAULT)){
                    openBottomSheetDialogOptionsCameraOrGallery(resultCode)
                } else {
                    openBottomSheetDialogOptionsEditOrDelete(resultCode)
                }
            }
            Constant.RESULT_CODE_CV_IMG_POST_2 -> {
                if (binding.ivImgPost2.tag.equals(Constant.TAG_DEFAULT)){
                    openBottomSheetDialogOptionsCameraOrGallery(resultCode)
                } else {
                    openBottomSheetDialogOptionsEditOrDelete(resultCode)
                }
            }
            Constant.RESULT_CODE_CV_IMG_POST_3 -> {
                if (binding.ivImgPost3.tag.equals(Constant.TAG_DEFAULT)){
                    openBottomSheetDialogOptionsCameraOrGallery(resultCode)
                } else {
                    openBottomSheetDialogOptionsEditOrDelete(resultCode)
                }
            }
            Constant.RESULT_CODE_CV_IMG_POST_4 -> {
                if (binding.ivImgPost4.tag.equals(Constant.TAG_DEFAULT)){
                    openBottomSheetDialogOptionsCameraOrGallery(resultCode)
                } else {
                    openBottomSheetDialogOptionsEditOrDelete(resultCode)
                }
            }
        }
    }

    /**
     * Metodo para abrir el bottom sheet dialog para eliminar
     * o editar una imagen ya capturada
     */
    private fun openBottomSheetDialogOptionsEditOrDelete(resultCode: Int) {
        toast("Abriendo editar o eliminar")
    }

    /**
     * Metodo para abrir el bottom sheet dialog para abrir la camara
     * o abrir la galeria
     */
    private fun openBottomSheetDialogOptionsCameraOrGallery(resultCode: Int) {
        toast("Abriendo camara o galeria")
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
     * Metodo que configura los editext como botones de seleccion
     */
    private fun configInputsSelectors() {
        binding.apply {
            tietCategory.setOnClickListener { this@PostProductActivity.reload() }
            tietNegotiable.setOnClickListener { this@PostProductActivity.reload() }
            tietProductStatus.setOnClickListener { this@PostProductActivity.reload() }
        }
    }

    /**
     * Metodo que valida todos los inputs
     */
    private fun validarInputs() {
        toast("Validando inputs")
    }
}