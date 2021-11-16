package com.app.millennium.ui.activities.post_product

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.app.millennium.R
import com.app.millennium.core.common.*
import com.app.millennium.databinding.ActivityPostProductBinding
import com.app.millennium.databinding.ViewBottomSheetOptionsSourceImagesBinding
import com.app.millennium.ui.activities.home.HomeActivity
import com.google.android.material.bottomsheet.BottomSheetDialog

class PostProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostProductBinding

    /*
     * Variable para obtener un flag para saber siempre qué
     * input de las imágenes se ha pulsado
     */
    private var resultCodeImageSalected: Int = 0

    /**
     * Launcher para abrir la camara y tener la lógica
     * para saber desde que input de las imagenes ha
     * sido lanzada.
     */
    private val launcherCameraImage1 =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            toast("Salir de la imagen 1")
            toast("${it.data?.extras}")
        }

    private val launcherCameraImage2 =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            toast("Salir de la imagen 2")
            toast("${it.data?.extras}")
        }

    private val launcherCameraImage3 =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            toast("Salir de la imagen 3")
            toast("${it.data?.extras}")
        }

    private val launcherCameraImage4 =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            toast("Salir de la imagen 4")
            toast("${it.data?.extras}")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initObservables()
    }

    /**
     * Metodo sobreescrito para que cuando se pulse el botón del
     * sistema ir hacia atrás la activity muera y levante el home
     * como una activity nueva
     */
    override fun onBackPressed() {
        super.onBackPressed()
        openActivity<HomeActivity> {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }

    /**
     * Metodo sobreescrito de la activity para obtener requestcode de los permisos que se vayan a pedir
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            //Si el permiso es de la camara
            Constant.PERMISSION_CAMERA -> {
                //Ahora obtenemos esos permisos aceptados o rechazados
                if (grantResults[0].isNotNull()
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ){
                    openCamera()
                }
            }
        }
    }

    private fun initUI(){
        binding.ivBack.setOnClickListener {onBackPressed()} //Boton para ir hacia atras
        configImages() //Seleccionar imagenes
        configInputsSelectors() //Campos selectores
        configPostProduct() //Boton para publicar el producto
    }

    private fun initObservables() {

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
                resultCodeImageSalected = Constant.RESULT_CODE_CV_IMG_POST_1
                configBottomSheetOption()
            }
            cvImgPost2.setOnClickListener {
                this@PostProductActivity.reload()
                resultCodeImageSalected = Constant.RESULT_CODE_CV_IMG_POST_2
                configBottomSheetOption()
            }
            cvImgPost3.setOnClickListener {
                this@PostProductActivity.reload()
                resultCodeImageSalected = Constant.RESULT_CODE_CV_IMG_POST_3
                configBottomSheetOption()
            }
            cvImgPost4.setOnClickListener {
                this@PostProductActivity.reload()
                resultCodeImageSalected = Constant.RESULT_CODE_CV_IMG_POST_4
                configBottomSheetOption()
            }
        }
    }

    /**
     * Metodo para abrir el bottomSheet de las acciones
     * para abrir la cámara o la galeria o si ya tiene una
     * imagen abrir el botttomSheet que da la accion
     * sobre eliminar la imagen o editarla
     */
    private fun configBottomSheetOption() {
        when (resultCodeImageSalected){
            /**
             * El requestCode se necesita para establecer luego la imagen capturada a un inputs
             * con el resultCode le aplicamos esta lógica para llevar el control de los inputs
             */
            Constant.RESULT_CODE_CV_IMG_POST_1 -> {
                //Ahora debemos comprobar si tiene una imagen o no tiene una imagen
                //Verificando que el tag sea default
                //Si es default significa que no tiene ninguna imagen
                if (binding.ivImgPost1.tag.equals(Constant.TAG_DEFAULT)){
                    openBottomSheetDialogOptionsCameraOrGallery()
                } else {
                    openBottomSheetDialogOptionsEditOrDelete()
                }
            }
            Constant.RESULT_CODE_CV_IMG_POST_2 -> {
                if (binding.ivImgPost2.tag.equals(Constant.TAG_DEFAULT)){
                    openBottomSheetDialogOptionsCameraOrGallery()
                } else {
                    openBottomSheetDialogOptionsEditOrDelete()
                }
            }
            Constant.RESULT_CODE_CV_IMG_POST_3 -> {
                if (binding.ivImgPost3.tag.equals(Constant.TAG_DEFAULT)){
                    openBottomSheetDialogOptionsCameraOrGallery()
                } else {
                    openBottomSheetDialogOptionsEditOrDelete()
                }
            }
            Constant.RESULT_CODE_CV_IMG_POST_4 -> {
                if (binding.ivImgPost4.tag.equals(Constant.TAG_DEFAULT)){
                    openBottomSheetDialogOptionsCameraOrGallery()
                } else {
                    openBottomSheetDialogOptionsEditOrDelete()
                }
            }
        }
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
            tietCategory.setOnClickListener {
                this@PostProductActivity.reload()
                configSelectorCategory()
            }
            tietNegotiable.setOnClickListener {
                this@PostProductActivity.reload()
                configSelectorNegotiable()
            }
            tietProductStatus.setOnClickListener {
                this@PostProductActivity.reload()
                configSelectorProductStatus()
            }
        }
    }

    /**
     * Metodo para configurar el selector de las categorias
     */
    private fun configSelectorCategory() {

        //Creamos un listview para mostrar todas las categorias
        val listViewOptions = ListView(this)
        //Creamos la lista de todas las categorias
        val listOptions = mutableListOf(
            getString(R.string.cat_coches),
            getString(R.string.cat_motos_bicicleta),
            getString(R.string.cat_motor_accesorios),
            getString(R.string.cat_moda_accesorios),
            getString(R.string.cat_inmobiliaria),
            getString(R.string.cat_informatica_electronica),
            getString(R.string.cat_deporte_ocio),
            getString(R.string.cat_consolas_videojuegos),
            getString(R.string.cat_hogar_jardin),
            getString(R.string.cat_electrodomésticos),
            getString(R.string.cat_cine_libros_música),
            getString(R.string.cat_niños_bebes),
            getString(R.string.cat_coleccionismo),
            getString(R.string.cat_otros)
        )
        //Creamos el adapter para setear en una vista la lista de categorias
        val adapter = ArrayAdapter(this, R.layout.view_alertdialog_info_product, R.id.mtv_parametro, listOptions)
        //Seteamos el adapter al listview para mostrar la lista de categorias
        listViewOptions.adapter = adapter
        //Creamos el alertDialog para setear el listview a este alertDialog y se lance
        val adbCategories = AlertDialog.Builder(this).setView(listViewOptions)
        val adCategories = adbCategories.create()
        adCategories.show()
        //Dar funcionalidad al listView para cuando se seleccione una categoria
        listViewOptions.setOnItemClickListener { adapterView, view, i, l ->
            binding.tietCategory.setText(listOptions[i])
            adCategories.dismiss()
        }
    }

    /**
     * Metodo para configurar el selector del selector negotiable
     */
    private fun configSelectorNegotiable() {

        //Creamos un listview para mostrar una lista
        val listViewOptions = ListView(this)
        //Creamos la lista
        val listOptions = mutableListOf(
            getString(R.string.neg_no),
            getString(R.string.neg_si)
        )
        //Creamos el adapter para setear en una vista la lista
        val adapter = ArrayAdapter(this, R.layout.view_alertdialog_info_product, R.id.mtv_parametro, listOptions)
        //Seteamos el adapter al listview para mostrar la lista
        listViewOptions.adapter = adapter
        //Creamos el alertDialog para setear el listview a este alertDialog y se lance
        val adbNegotiables = AlertDialog.Builder(this).setView(listViewOptions)
        val adNegotiables = adbNegotiables.create()
        adNegotiables.show()
        //Dar funcionalidad al listView para cuando se seleccione un item del listview
        listViewOptions.setOnItemClickListener { adapterView, view, i, l ->
            binding.tietNegotiable.setText(listOptions[i])
            adNegotiables.dismiss()
        }
    }

    /**
     * Metodo para configurar el selector de los estados
     */
    private fun configSelectorProductStatus() {

        //Creamos un listview para mostrar todas las categorias
        val listViewOptions = ListView(this)
        //Creamos la lista de todas las categorias
        val listOptions = mutableListOf(
            getString(R.string.est_sin_abrir),
            getString(R.string.est_nuevo),
            getString(R.string.est_como_nuevo),
            getString(R.string.est_buen_estado),
            getString(R.string.est_condiciones_aceptables),
            getString(R.string.est_mucho_uso)
        )
        //Creamos el adapter para setear en una vista la lista
        val adapter = ArrayAdapter(this, R.layout.view_alertdialog_info_product, R.id.mtv_parametro, listOptions)
        //Seteamos el adapter al listview para mostrar la lista
        listViewOptions.adapter = adapter
        //Creamos el alertDialog para setear el listview a este alertDialog y se lance
        val adbProductStatus = AlertDialog.Builder(this).setView(listViewOptions)
        val adProductStatus = adbProductStatus.create()
        adProductStatus.show()
        //Dar funcionalidad al listView para cuando se seleccione un item del listView
        listViewOptions.setOnItemClickListener { adapterView, view, i, l ->
            binding.tietProductStatus.setText(listOptions[i])
            adProductStatus.dismiss()
        }
    }

    /**
     * Metodo para abrir el bottom sheet dialog para abrir la camara
     * o abrir la galeria
     */
    private fun openBottomSheetDialogOptionsCameraOrGallery() {
        //Creamos el bottom sheet dialog con el estilo qeu predefinimos
        //para los bottom sheet dialog
        val bottomSheetDialogOptionsCameraOrGallery =
            BottomSheetDialog(this, R.style.BottomSheetTheme)

        //Obtenemos la vista del layout bottomsheetDialog y la bindeamos
        var bindingBottomSheetDialog: ViewBottomSheetOptionsSourceImagesBinding? =
            ViewBottomSheetOptionsSourceImagesBinding.inflate(
                LayoutInflater.from(this)
            )

        //Le seteamos la vista al BottomSheetDialog creado
        bottomSheetDialogOptionsCameraOrGallery.setContentView(
            bindingBottomSheetDialog!!.root
        )

        //Y lo mostramos
        bottomSheetDialogOptionsCameraOrGallery.show()

        //Seteamos los metodos setOnClicListener a las imagesViews
        //de la vista del bottom sheet dialog
        bindingBottomSheetDialog.apply {
            ivCamera.setOnClickListener {
                toast("Camera")
                bottomSheetDialogOptionsCameraOrGallery.dismiss()
                bindingBottomSheetDialog = null
                //Si se seleciona la cámara entonces se abrirá la cámara
                /**
                 * Primero chequeamos los permisos de la camara
                 */
                checkPermissionCamera()
            }
            ivGallery.setOnClickListener {
                toast("Galeria")
                bottomSheetDialogOptionsCameraOrGallery.dismiss()
                bindingBottomSheetDialog = null
                //Si se seleciona la galería entonces se abrirá la galería
                //openGallery(resultCode)
            }
        }
    }

    /**
     * Metodo para abrir el bottom sheet dialog para eliminar
     * o editar una imagen ya capturada
     */
    private fun openBottomSheetDialogOptionsEditOrDelete() {
        toast("Abriendo editar o eliminar")
    }

    /**
     * Metodo para abrir la cámara
     */
    private fun openCamera() {
        val intentCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intentCamera.putExtra("saludo", "hola")

        when (resultCodeImageSalected) {
            Constant.RESULT_CODE_CV_IMG_POST_1 -> {
                launcherCameraImage1.launch(intentCamera)
            }
            Constant.RESULT_CODE_CV_IMG_POST_2 -> {
                launcherCameraImage2.launch(intentCamera)
            }
            Constant.RESULT_CODE_CV_IMG_POST_3 -> {
                launcherCameraImage3.launch(intentCamera)
            }
            Constant.RESULT_CODE_CV_IMG_POST_4 -> {
                launcherCameraImage4.launch(intentCamera)
            }
        }

    }

    /**
     * Metodo para verificar si los permisos están aceptados o rechazados
     */
    private fun checkPermissionCamera() {

        //Verificamos si el permiso ya se ha pedido por el momento y verificamos si están aceptados
        if (
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ){
            //Si no ha sido aceptado verificamos si han sido rechazados
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
                //Los permisos de la camara ya están denegados
                toast(getString(R.string.msg_activar_permisos_camara), Toast.LENGTH_LONG)
            } else {
                //De lo contrario significa que nunca se han pedido los permisos, se piden
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), Constant.PERMISSION_CAMERA)
            }

        } else {
            //El permiso ya ha sido aceptado
            openCamera()
        }
    }

    /**
     * Metodo que valida todos los inputs
     */
    private fun validarInputs() {
        toast("Validando inputs")
    }
}