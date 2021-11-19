package com.app.millennium.ui.activities.post_product

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import com.app.millennium.R
import com.app.millennium.core.common.*
import com.app.millennium.core.utils.FileUtil
import com.app.millennium.databinding.ActivityPostProductBinding
import com.app.millennium.databinding.ViewBottomSheetOptionsSourceImagesBinding
import com.app.millennium.ui.activities.home.HomeActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import java.io.File
import java.lang.Exception
import java.util.*

class PostProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostProductBinding

    //Ficheros para almacenar las imagenes
    private var fileImage1: File? = null
    private var fileImage2: File? = null
    private var fileImage3: File? = null
    private var fileImage4: File? = null

    //Paths de las imagenes de la camara
    private var photoPath1: String? = null
    private var photoPath2: String? = null
    private var photoPath3: String? = null
    private var photoPath4: String? = null

    //AbsolutePaths de las imagenes de la camara
    private var photoAbsolutePath1: String? = null
    private var photoAbsolutePath2: String? = null
    private var photoAbsolutePath3: String? = null
    private var photoAbsolutePath4: String? = null

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
            if (it.resultCode == RESULT_OK){
                Picasso.get().load(photoPath1).into(
                    binding.ivImgPost1
                )
                binding.ivImgPost1.tag = Constant.TAG_NOT_DEFAULT
                /**
                 * Quitamos el textview del error
                 */
                if (binding.mtvErrorImagenes.isVisible)
                    binding.mtvErrorImagenes.visibility = View.GONE
            }
        }

    private val launcherCameraImage2 =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            if (it.resultCode == RESULT_OK){
                Picasso.get().load(photoPath2).into(
                    binding.ivImgPost2
                )
                binding.ivImgPost2.tag = Constant.TAG_NOT_DEFAULT
                /**
                 * Quitamos el textview del error
                 */
                if (binding.mtvErrorImagenes.isVisible)
                    binding.mtvErrorImagenes.visibility = View.GONE
            }
        }

    private val launcherCameraImage3 =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            if (it.resultCode == RESULT_OK){
                Picasso.get().load(photoPath3).into(
                    binding.ivImgPost3
                )
                binding.ivImgPost3.tag = Constant.TAG_NOT_DEFAULT
                /**
                 * Quitamos el textview del error
                 */
                if (binding.mtvErrorImagenes.isVisible)
                    binding.mtvErrorImagenes.visibility = View.GONE
            }
        }

    private val launcherCameraImage4 =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            if (it.resultCode == RESULT_OK){
                Picasso.get().load(photoPath4).into(
                    binding.ivImgPost4
                )
                binding.ivImgPost4.tag = Constant.TAG_NOT_DEFAULT
                /**
                 * Quitamos el textview del error
                 */
                if (binding.mtvErrorImagenes.isVisible)
                    binding.mtvErrorImagenes.visibility = View.GONE
            }
        }

    /**
     * Launcher para abrir la galería y tener la lógica
     * para saber desde que input de las imagenes ha
     * sido lanzada.
     */
    private val launcherGalleryImage1 =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            if (it.resultCode == RESULT_OK){
                /**
                 * Si el result code es ok
                 * significa que se ha seleccionado
                 * una imagen de la galeria
                 */
                try {
                    /**
                     * La seleccion de la imagen obtenemos la uri
                     * de la imagen y construimos un fichero
                     * en base a la uri obtenida
                     */
                    fileImage1 = FileUtil.from(
                        this,
                        it.data?.data!!
                    )
                    /**
                     * Le seteamos la imagen al input del que se haya
                     * pulsado para abrir la galeria
                     */
                    binding.ivImgPost1.setImageBitmap(
                        BitmapFactory.decodeFile(
                            fileImage1?.absolutePath
                        )
                    )
                    /**
                     * Le seteamos el tag al imageview donde
                     * ha sido establecida la imagen para
                     * saber siempre si el input tiene una imagen
                     * o no la tiene
                     */
                    binding.ivImgPost1.tag = Constant.TAG_NOT_DEFAULT
                    /**
                     * Y por último quitamos el textview del error
                     */
                    if (binding.mtvErrorImagenes.isVisible)
                        binding.mtvErrorImagenes.visibility = View.GONE

                } catch (e: Exception){
                    /**
                     * Al crear el fichero puede lanzarse una excepcion
                     * al cargar la imagen
                     */
                    toast(getString(R.string.msg_error_cargar_imagen_galeria))
                }
            }
        }

    private val launcherGalleryImage2 =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            if (it.resultCode == RESULT_OK){

                /**
                 * Si el result code es ok
                 * significa que se ha seleccionado
                 * una imagen de la galeria
                 */
                try {
                    /**
                     * La seleccion de la imagen obtenemos la uri
                     * de la imagen y construimos un fichero
                     * en base a la uri obtenida
                     */
                    fileImage2 = FileUtil.from(
                        this,
                        it.data?.data!!
                    )
                    /**
                     * Le seteamos la imagen al input del que se haya
                     * pulsado para abrir la galeria
                     */
                    binding.ivImgPost2.setImageBitmap(
                        BitmapFactory.decodeFile(
                            fileImage2?.absolutePath
                        )
                    )
                    /**
                     * Le seteamos el tag al imageview donde
                     * ha sido establecida la imagen para
                     * saber siempre si el input tiene una imagen
                     * o no la tiene
                     */
                    binding.ivImgPost2.tag = Constant.TAG_NOT_DEFAULT
                    /**
                     * Y por último quitamos el textview del error
                     */
                    if (binding.mtvErrorImagenes.isVisible)
                        binding.mtvErrorImagenes.visibility = View.GONE

                } catch (e: Exception){
                    /**
                     * Al crear el fichero puede lanzarse una excepcion
                     * al cargar la imagen
                     */
                    toast(getString(R.string.msg_error_cargar_imagen_galeria))
                }
            }
        }

    private val launcherGalleryImage3 =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            if (it.resultCode == RESULT_OK){
                /**
                 * Si el result code es ok
                 * significa que se ha seleccionado
                 * una imagen de la galeria
                 */
                try {
                    /**
                     * La seleccion de la imagen obtenemos la uri
                     * de la imagen y construimos un fichero
                     * en base a la uri obtenida
                     */
                    fileImage3 = FileUtil.from(
                        this,
                        it.data?.data!!
                    )
                    /**
                     * Le seteamos la imagen al input del que se haya
                     * pulsado para abrir la galeria
                     */
                    binding.ivImgPost3.setImageBitmap(
                        BitmapFactory.decodeFile(
                            fileImage3?.absolutePath
                        )
                    )
                    /**
                     * Le seteamos el tag al imageview donde
                     * ha sido establecida la imagen para
                     * saber siempre si el input tiene una imagen
                     * o no la tiene
                     */
                    binding.ivImgPost3.tag = Constant.TAG_NOT_DEFAULT
                    /**
                     * Y por último quitamos el textview del error
                     */
                    if (binding.mtvErrorImagenes.isVisible)
                        binding.mtvErrorImagenes.visibility = View.GONE

                } catch (e: Exception){
                    /**
                     * Al crear el fichero puede lanzarse una excepcion
                     * al cargar la imagen
                     */
                    toast(getString(R.string.msg_error_cargar_imagen_galeria))
                }
            }
        }

    private val launcherGalleryImage4 =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            if (it.resultCode == RESULT_OK){
                /**
                 * Si el result code es ok
                 * significa que se ha seleccionado
                 * una imagen de la galeria
                 */
                try {
                    /**
                     * La seleccion de la imagen obtenemos la uri
                     * de la imagen y construimos un fichero
                     * en base a la uri obtenida
                     */
                    fileImage4 = FileUtil.from(
                        this,
                        it.data?.data!!
                    )
                    /**
                     * Le seteamos la imagen al input del que se haya
                     * pulsado para abrir la galeria
                     */
                    binding.ivImgPost4.setImageBitmap(
                        BitmapFactory.decodeFile(
                            fileImage4?.absolutePath
                        )
                    )
                    /**
                     * Le seteamos el tag al imageview donde
                     * ha sido establecida la imagen para
                     * saber siempre si el input tiene una imagen
                     * o no la tiene
                     */
                    binding.ivImgPost4.tag = Constant.TAG_NOT_DEFAULT
                    /**
                     * Y por último quitamos el textview del error
                     */
                    if (binding.mtvErrorImagenes.isVisible)
                        binding.mtvErrorImagenes.visibility = View.GONE

                } catch (e: Exception){
                    /**
                     * Al crear el fichero puede lanzarse una excepcion
                     * al cargar la imagen
                     */
                    toast(getString(R.string.msg_error_cargar_imagen_galeria))
                }
            }
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
                } else {
                    toast(getString(R.string.msg_permisos_denegados))
                }
            }
            //Si el permiso es de la galeria
            Constant.PERMISSION_GALLERY -> {
                if(grantResults[0].isNotNull()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ){
                    openGallery()
                } else {
                    toast(getString(R.string.msg_permisos_denegados))
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

            ivImgPost1.setOnClickListener {
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
            validateFields()
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

        //Creamos un listview para mostrar una lista
        val listViewOptions = ListView(this)
        //Creamos la lista
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
        //Creamos el adapter para setear en una vista la lista
        val adapter = ArrayAdapter(this, R.layout.view_alertdialog_info_product, R.id.mtv_parametro, listOptions)
        //Seteamos el adapter al listview para mostrar la lista
        listViewOptions.adapter = adapter
        //Creamos el alertDialog para setear el listview a este alertDialog y se lance
        val adbCategories = AlertDialog.Builder(this).setView(listViewOptions)
        val adCategories = adbCategories.create()
        adCategories.show()
        //Dar funcionalidad al listView para cuando se seleccione un item de la lista
        listViewOptions.setOnItemClickListener { _, _, pos, _ ->
            binding.tietCategory.setText(listOptions[pos])
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
            getString(R.string.neg_si),
            getString(R.string.sel_sin_especificar)
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
        listViewOptions.setOnItemClickListener { _, _, pos, _ ->
            binding.tietNegotiable.setText(listOptions[pos])
            adNegotiables.dismiss()
        }
    }

    /**
     * Metodo para configurar el selector de los estados
     */
    private fun configSelectorProductStatus() {

        //Creamos un listview para mostrar una lista
        val listViewOptions = ListView(this)
        //Creamos la lista
        val listOptions = mutableListOf(
            getString(R.string.est_sin_abrir),
            getString(R.string.est_nuevo),
            getString(R.string.est_como_nuevo),
            getString(R.string.est_buen_estado),
            getString(R.string.est_condiciones_aceptables),
            getString(R.string.est_mucho_uso),
            getString(R.string.sel_sin_especificar)
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
        listViewOptions.setOnItemClickListener { _, _, pos, _ ->
            binding.tietProductStatus.setText(listOptions[pos])
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
                bottomSheetDialogOptionsCameraOrGallery.dismiss()
                bindingBottomSheetDialog = null
                //Si se seleciona la cámara entonces se abrirá la cámara
                /**
                 * Primero chequeamos los permisos de la camara
                 */
                checkPermissionCamera()
            }
            ivGallery.setOnClickListener {
                bottomSheetDialogOptionsCameraOrGallery.dismiss()
                bindingBottomSheetDialog = null
                //Si se seleciona la galería entonces se chequearán los permisos de la galería
                checkPermissionGallery()
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
        //Creamos el intent para abrir la camara
        val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //Creamos un fichero para guardar la imagen en un fichero
        //en una ruta del sistema
        var filePhoto: File? = null

        try {
            //Creamos el fichero para almacenar la foto
            filePhoto = createFilePhoto()
        } catch (e: Exception){
            toast(e.message!!)
        }

        //Verificamos que no sea nulo
        if (filePhoto.isNotNull()){
            /**
             * Si no es nulo creamos la uri que referencia al fichero
             * para pasárselo al intent de la camara
             * Para que pueda almacenar la imagen en el fichero
             */

            /**
             * Además se debe configurar un provider en el manifest
             * e indicarle a este provider el path con un fichero xml
             * con el path definido
             */
            val photoUri = FileProvider.getUriForFile(
                this,
                Constant.PACKAGE_PROJECT,
                filePhoto!!
            )
            camera.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        }

        /**
         * Ahora dependiendo del input seleccionado
         * se lanzará el launcher correspondiente
         */
        when (resultCodeImageSalected) {
            Constant.RESULT_CODE_CV_IMG_POST_1 -> {
                launcherCameraImage1.launch(camera)
            }
            Constant.RESULT_CODE_CV_IMG_POST_2 -> {
                launcherCameraImage2.launch(camera)
            }
            Constant.RESULT_CODE_CV_IMG_POST_3 -> {
                launcherCameraImage3.launch(camera)
            }
            Constant.RESULT_CODE_CV_IMG_POST_4 -> {
                launcherCameraImage4.launch(camera)
            }
        }
    }

    /**
     * Metodo que crea un fichero con un nombre
     * mas la ruta de donde se va a crear el fichero
     */
    private fun createFilePhoto(): File? {
        //Obtenemos el directorio de Pictures
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        //Construimo el fichero con el nombre, la extension y la ruta de almacenaje
        val filePhoto = File.createTempFile(
            "${Date()}_photo",
            ".jpg",
            storageDir
        )

        /**
         * Ahora dependiendo de que input se seleccionó
         * se guardará la ruta en las var correspondientes
         * y retornamos el fichero construido
         */
        when (resultCodeImageSalected){
            Constant.RESULT_CODE_CV_IMG_POST_1 -> {
                photoPath1 = "file:${filePhoto.absolutePath}"
                photoAbsolutePath1 = filePhoto.absolutePath
                return filePhoto
            }
            Constant.RESULT_CODE_CV_IMG_POST_2 -> {
                photoPath2 = "file:${filePhoto.absolutePath}"
                photoAbsolutePath2 = filePhoto.absolutePath
                return filePhoto
            }
            Constant.RESULT_CODE_CV_IMG_POST_3 -> {
                photoPath3 = "file:${filePhoto.absolutePath}"
                photoAbsolutePath3 = filePhoto.absolutePath
                return filePhoto
            }
            Constant.RESULT_CODE_CV_IMG_POST_4 -> {
                photoPath4 = "file:${filePhoto.absolutePath}"
                photoAbsolutePath4 = filePhoto.absolutePath
                return filePhoto
            }
            else -> {
                return null
            }
        }
    }

    /**
     * Metodo para abrir la galería
     */
    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_GET_CONTENT)
        gallery.type = "image/"

        when (resultCodeImageSalected) {
            Constant.RESULT_CODE_CV_IMG_POST_1 -> {
                launcherGalleryImage1.launch(gallery)
            }
            Constant.RESULT_CODE_CV_IMG_POST_2 -> {
                launcherGalleryImage2.launch(gallery)
            }
            Constant.RESULT_CODE_CV_IMG_POST_3 -> {
                launcherGalleryImage3.launch(gallery)
            }
            Constant.RESULT_CODE_CV_IMG_POST_4 -> {
                launcherGalleryImage4.launch(gallery)
            }
        }
    }

    /**
     * Metodo para verificar si los permisos de la camara
     * están aceptados o rechazados
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
     * Metodo para verificar si los permisos de la galeria
     * están aceptados o rechazados
     */
    private fun checkPermissionGallery() {

        //Verificamos si el permiso ya se ha pedido por el momento y verificamos si están aceptados
        if (
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ){
            //Si no ha sido aceptado verificamos si han sido rechazados
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                //Los permisos de la camara ya están denegados
                toast(getString(R.string.msg_activar_permisos_galeria), Toast.LENGTH_LONG)
            } else {
                //De lo contrario significa que nunca se han pedido los permisos, se piden
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), Constant.PERMISSION_GALLERY)
            }

        } else {
            //El permiso ya ha sido aceptado
            openGallery()
        }
    }

    /**
     * Metodo que valida todos los inputs
     */
    private fun validateFields() {
        if (isUploadedImages()){
            //Quitar el error de las imagenes vacías
            toast("imagen cargada")
        } else {
            //Poner error de las imagenes
            binding.mtvErrorImagenes.visibility = View.VISIBLE
        }
    }

    /**
     * Metodo que valida si hay alguna imagen cargada
     * mediante el tag del imageview
     */
    private fun isUploadedImages(): Boolean {
        if (
            binding.ivImgPost1.tag.equals(Constant.TAG_DEFAULT)
            && binding.ivImgPost2.tag.equals(Constant.TAG_DEFAULT)
            && binding.ivImgPost3.tag.equals(Constant.TAG_DEFAULT)
            && binding.ivImgPost4.tag.equals(Constant.TAG_DEFAULT)
        ) {
            return false
        }
        return true
    }
}