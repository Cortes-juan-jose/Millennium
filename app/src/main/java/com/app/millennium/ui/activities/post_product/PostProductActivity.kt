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
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import com.app.millennium.R
import com.app.millennium.core.common.*
import com.app.millennium.core.utils.CompressBitmapImage
import com.app.millennium.core.utils.ConfigThemeApp
import com.app.millennium.core.utils.FileUtil
import com.app.millennium.data.model.Product
import com.app.millennium.data.model.User
import com.app.millennium.databinding.ActivityPostProductBinding
import com.app.millennium.databinding.ViewBottomSheetOptionsImagesSelectedBinding
import com.app.millennium.databinding.ViewBottomSheetOptionsSourceImagesBinding
import com.app.millennium.ui.activities.home.HomeActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import java.io.File
import java.util.*

class PostProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostProductBinding
    private val viewModel : PostProductViewModel by viewModels()

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

    //Products modelo
    private lateinit var product: Product
    //AlertDialog
    private lateinit var dialogLoading: android.app.AlertDialog

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
                fileImage1 = File(photoAbsolutePath1!!)
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
                fileImage2 = File(photoAbsolutePath2!!)
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
                fileImage3 = File(photoAbsolutePath3!!)
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
                fileImage4 = File(photoAbsolutePath4!!)
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
                     * A continuación verificamos si la extension
                     * del fichero es permitido porque puede ser
                     * que el usuario elija un archivo que no sea una
                     * imagen (formatos permitidos = JPEG, PNG, JPG)
                     */
                    if (fileImage1!!.permitted()){
                        //Si el formato es permitido entonces:

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
                    } else {
                        toast(getString(R.string.msg_error_archivo_no_permitido))
                        fileImage1 = null
                    }

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
                     * A continuación verificamos si la extension
                     * del fichero es permitido porque puede ser
                     * que el usuario elija un archivo que no sea una
                     * imagen (formatos permitidos = JPEG, PNG, JPG)
                     */
                    if (fileImage2!!.permitted()){
                        //Si el formato es permitido entonces:

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
                    } else {
                        toast(getString(R.string.msg_error_archivo_no_permitido))
                        fileImage2 = null
                    }

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
                     * A continuación verificamos si la extension
                     * del fichero es permitido porque puede ser
                     * que el usuario elija un archivo que no sea una
                     * imagen (formatos permitidos = JPEG, PNG, JPG)
                     */
                    if (fileImage3!!.permitted()){
                        //Si el formato es permitido entonces:

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
                    } else {
                        toast(getString(R.string.msg_error_archivo_no_permitido))
                        fileImage3 = null
                    }

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
                     * A continuación verificamos si la extension
                     * del fichero es permitido porque puede ser
                     * que el usuario elija un archivo que no sea una
                     * imagen (formatos permitidos = JPEG, PNG, JPG)
                     */
                    if (fileImage4!!.permitted()){
                        //Si el formato es permitido entonces:

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
                    } else {
                        toast(getString(R.string.msg_error_archivo_no_permitido))
                        fileImage4 = null
                    }

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
                    toast(getString(R.string.msg_info_permisos_denegados))
                }
            }
            //Si el permiso es de la galeria
            Constant.PERMISSION_GALLERY -> {
                if(grantResults[0].isNotNull()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ){
                    openGallery()
                } else {
                    toast(getString(R.string.msg_info_permisos_denegados))
                }
            }
        }
    }

    /**
     * Inicializar todos los componentes de la ui
     */
    private fun initUI(){

        dialogLoading = SpotsDialog
            .Builder()
            .setContext(this)
            .setCancelable(false)
            .build()

        binding.ivBack.setOnClickListener {onBackPressed()} //Boton para ir hacia atras
        configImages() //Seleccionar imagenes
        configInputsSelectors() //Campos selectores
        configPostProduct() //Boton para publicar el producto
        configEventTextWatcherPrice() //Evento para el campo del precio
    }

    /**
     * Metodo que lanza los observables del viewmodel asociado
     */
    private fun initObservables() {

        //Observer para cuando se guarde la primera imagen
        viewModel.saveImage1.observe(
            this,
            {
                it.addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        //Si se guarda la primera obtenemos la url
                        viewModel.getUrlImage(Constant.RESULT_CODE_CV_IMG_POST_1)

                    } else {
                        dialogLoading.dismiss()
                        toast(getString(R.string.msg_error_guardar_imagen))
                    }
                }
                it.addOnFailureListener{ exc ->
                    toast("${exc.message}")
                }
            }
        )

        //Observer para cuando se guarde la segunda imagen
        viewModel.saveImage2.observe(
            this,
            {
                it.addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        viewModel.getUrlImage(Constant.RESULT_CODE_CV_IMG_POST_2)
                    } else {
                        dialogLoading.dismiss()
                        toast(getString(R.string.msg_error_guardar_imagen))
                    }
                }
                it.addOnFailureListener{ exc ->
                    toast("${exc.message}")
                }
            }
        )

        //Observer para cuando se guarde la tercera imagen
        viewModel.saveImage3.observe(
            this,
            {
                it.addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        viewModel.getUrlImage(Constant.RESULT_CODE_CV_IMG_POST_3)
                    } else {
                        dialogLoading.dismiss()
                        toast(getString(R.string.msg_error_guardar_imagen))
                    }
                }
                it.addOnFailureListener{ exc ->
                    toast("${exc.message}")
                }
            }
        )

        //Observer para cuando se guarde la cuarta imagen
        viewModel.saveImage4.observe(
            this,
            {
                it.addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        //Si la cuarta imagen ha sido subida entonces yan han sido todas subidas
                        viewModel.getUrlImage(Constant.RESULT_CODE_CV_IMG_POST_4)
                    } else {
                        dialogLoading.dismiss()
                        toast(getString(R.string.msg_error_guardar_imagen))
                    }
                }
                it.addOnFailureListener{ exc ->
                    toast("${exc.message}")
                }
            }
        )

        //Observer para obtener la url image 1
        viewModel.getUrlImage1.observe(
            this,
            {
                it?.let { task ->
                    task.addOnSuccessListener { uri ->
                        uri?.let { _user ->
                            product.image1 = _user.toString()
                            //Si la primera imagen ha sido subida, verificamos la 2, 3, 4
                            when {
                                fileImage2.isNotNull() -> {
                                    //Construimos la imagen en bytes
                                    val imageByte = CompressBitmapImage.getImage(
                                        this,
                                        fileImage2?.path,
                                        Constant.WIDTH_IMAGE_STORAGE,
                                        Constant.HEIGHT_IMAGE_STORAGE)
                                    //y la guardamos
                                    viewModel.saveImage(imageByte, Constant.RESULT_CODE_CV_IMG_POST_2)
                                }
                                fileImage3.isNotNull() -> {
                                    //Construimos la imagen en bytes
                                    val imageByte = CompressBitmapImage.getImage(
                                        this,
                                        fileImage3?.path,
                                        Constant.WIDTH_IMAGE_STORAGE,
                                        Constant.HEIGHT_IMAGE_STORAGE)
                                    //y la guardamos
                                    viewModel.saveImage(imageByte, Constant.RESULT_CODE_CV_IMG_POST_3)
                                }
                                fileImage4.isNotNull() -> {
                                    //Construimos la imagen en bytes
                                    val imageByte = CompressBitmapImage.getImage(
                                        this,
                                        fileImage4?.path,
                                        Constant.WIDTH_IMAGE_STORAGE,
                                        Constant.HEIGHT_IMAGE_STORAGE)
                                    //y la guardamos
                                    viewModel.saveImage(imageByte, Constant.RESULT_CODE_CV_IMG_POST_4)
                                }
                                else -> {
                                    viewModel.getIdUser()
                                }
                            }
                        }
                    }
                    task.addOnFailureListener { exc ->
                        toast("${exc.message}")
                    }
                }
            }
        )
        //Observer para obtener la url image 1
        viewModel.getUrlImage2.observe(
            this,
            {
                it?.let { task ->
                    task.addOnSuccessListener { uri ->
                        uri?.let { _uri ->
                            product.image2 = _uri.toString()

                            //Si la segunda imagen ha sido subida, verificamos la 3, 4
                            when {
                                fileImage3.isNotNull() -> {
                                    //Construimos la imagen en bytes
                                    val imageByte = CompressBitmapImage.getImage(
                                        this,
                                        fileImage3?.path,
                                        Constant.WIDTH_IMAGE_STORAGE,
                                        Constant.HEIGHT_IMAGE_STORAGE)
                                    //y la guardamos
                                    viewModel.saveImage(imageByte, Constant.RESULT_CODE_CV_IMG_POST_3)
                                }
                                fileImage4.isNotNull() -> {
                                    //Construimos la imagen en bytes
                                    val imageByte = CompressBitmapImage.getImage(
                                        this,
                                        fileImage4?.path,
                                        Constant.WIDTH_IMAGE_STORAGE,
                                        Constant.HEIGHT_IMAGE_STORAGE)
                                    //y la guardamos
                                    viewModel.saveImage(imageByte, Constant.RESULT_CODE_CV_IMG_POST_4)
                                }
                                else -> { viewModel.getIdUser() }
                            }
                        }
                    }
                    task.addOnFailureListener { exc ->
                        toast("${exc.message}")
                    }
                }
            }
        )
        //Observer para obtener la url image 1
        viewModel.getUrlImage3.observe(
            this,
            {
                it?.let { task ->
                    task.addOnSuccessListener { uri ->
                        uri?.let { _uri ->
                            product.image3 = _uri.toString()

                            //Si la tercera imagen ha sido subida, verificamos la 4
                            when {
                                fileImage4.isNotNull() -> {
                                    //Construimos la imagen en bytes
                                    val imageByte = CompressBitmapImage.getImage(
                                        this,
                                        fileImage4?.path,
                                        Constant.WIDTH_IMAGE_STORAGE,
                                        Constant.HEIGHT_IMAGE_STORAGE)
                                    //y la guardamos
                                    viewModel.saveImage(imageByte, Constant.RESULT_CODE_CV_IMG_POST_4)
                                }
                                else -> {viewModel.getIdUser()}
                            }
                        }
                    }
                    task.addOnFailureListener { exc ->
                        toast("${exc.message}")
                    }
                }
            }
        )
        //Observer para obtener la url image 1
        viewModel.getUrlImage4.observe(
            this,
            {
                it?.let { task ->
                    task.addOnSuccessListener { uri ->
                        uri?.let { _uri ->
                            product.image4 = _uri.toString()
                            viewModel.getIdUser()
                        }
                    }
                    task.addOnFailureListener { exc ->
                        dialogLoading.dismiss()
                        toast("${exc.message}")
                    }
                }
            }
        )

        //Observer que guarda el producto
        viewModel.saveProduct.observe(
            this,
            {
                it?.let{
                    it.addOnCompleteListener { comp ->
                        dialogLoading.dismiss()
                        if (comp.isSuccessful){
                            //Cuando se guarde el producto actualizamos el valor en el usuario
                            viewModel.getUser(product.idUser!!)

                        } else {
                            toast(getString(R.string.msg_error_producto_publicado))
                        }
                    }
                    it.addOnFailureListener { e ->
                        toast(e.message!!)
                        dialogLoading.dismiss()
                    }
                }
            }
        )

        //Observer que obtiene el id del usuario
        viewModel.getIdUser.observe(
            this,
            {
                it?.let {
                    product.idUser = it
                    viewModel.saveProduct(product)
                }
            }
        )

        //Observer que obtiene el usuario del producto
        viewModel.getUser.observe(
            this,
            { task ->
                task?.let { _task ->
                    _task.addOnSuccessListener { document ->
                        document?.let { _document ->
                            if (_document.exists()){
                                //Obtenemos el usuario
                                val user = _document.toObject(User::class.java)
                                //Sumamos 1 a la propiedad uploadedProducts
                                user?.uploadedProducts = user?.uploadedProducts!! + 1
                                //Y lo actualizamos en la base de datos
                                viewModel.updateUploadedProducts(user)
                            } else {
                                dialogLoading.dismiss()
                                toast(getString(R.string.msg_error_usuario_no_existe))
                            }
                        }
                    }
                }
            }
        )

        //Observer para actualizar el campo uploadedProducts
        viewModel.updateUploadedProducts.observe(
            this,
            { task ->
                task?.let { _task ->
                    _task.addOnCompleteListener {
                        dialogLoading.dismiss()
                        toast(getString(R.string.msg_info_producto_publicado))
                        onBackPressed()
                    }
                    _task.addOnFailureListener {
                        dialogLoading.dismiss()
                        toast(it.message!!)
                    }
                }
            }
        )
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
        binding.apply {
            btnPost.setOnClickListener {
                this@PostProductActivity.reload()
                if (validateFields()){
                    //Mostrar una alerta de carga
                    dialogLoading.setMessage(getString(R.string.msg_alert_creando_producto))
                    dialogLoading.show()
                    //Guardamos el producto
                    saveProduct()
                }
            }
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

    private fun configEventTextWatcherPrice() {
        binding.apply {
            tietPrice.afterTextChanged {
                //si se introduce un punto al principio se le pondra un 0 delante
                if (it == ".") {
                    tietPrice.setText("0.")
                    //y el cursor se va al final
                    tietPrice.setSelection(tietPrice.text.toString().length)
                }
                //si se introduce dos 0 entonces no pondrá el segundo si no que pondrá un 0.
                if (it == "00") {
                    tietPrice.setText("0.")
                    //y el cursor se va al final
                    tietPrice.setSelection(tietPrice.text.toString().length)
                }
                //validar que solo admita 2 decimales
                if (tietPrice.text.toString().contains(".")) {
                    //si el texto que se ha introducido ya tiene un punto verificamos lo deseado
                    if (it.substring(it.lastIndexOf("."), it.length - 1).length > 2) {
                        tietPrice.setText(it.substring(0, it.length - 1))
                        //y el cursor se va al final
                        tietPrice.setSelection(tietPrice.text.toString().length)
                    }
                    //validar que solo permita 1 punto y no más
                    if (it.length > 2){
                        if (it[it.length-1] == '.'){
                            tietPrice.setText(it.substring(0, it.length-1))
                            tietPrice.setSelection(tietPrice.text.toString().length)
                        }
                    }
                }
                //validar que el punto no se pueda poner al final
                //Primero confirmamos si tiene 12 caracteres
                if (it.length == 12) {
                    //Si tiene 12 caracteres confirmamos si tiene un punto al final
                    if (it[11] == '.') {
                        tietPrice.setText(it.substring(0, it.length - 1))
                        //y el cursor se va al final
                        tietPrice.setSelection(tietPrice.text.toString().length)
                    }
                }
            }
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
        //Creamos el bottom sheet dialog con el estilo qeu predefinimos
        //para los bottom sheet dialog
        val bottomSheetDialogOptionsImagesSelected =
            BottomSheetDialog(this, R.style.BottomSheetTheme)
        //Obtenemos la vista del layout bottomsheetDialog y la bindeamos
        var bindingBottomSheetDialog: ViewBottomSheetOptionsImagesSelectedBinding? =
            ViewBottomSheetOptionsImagesSelectedBinding.inflate(
                LayoutInflater.from(this)
            )

        //Le seteamos la vista al BottomSheetDialog creado
        bottomSheetDialogOptionsImagesSelected.setContentView(
            bindingBottomSheetDialog!!.root
        )

        //Y lo mostramos
        bottomSheetDialogOptionsImagesSelected.show()

        bindingBottomSheetDialog.apply {

            mtvEdit.setOnClickListener {
                //Cerramos este dialog
                bottomSheetDialogOptionsImagesSelected.dismiss()
                //Quitamos el binding
                bindingBottomSheetDialog = null
                //Abrimos el bottom sheet de las opciones de la camara y la galeria
                openBottomSheetDialogOptionsCameraOrGallery()
            }
            mtvDelete.setOnClickListener {
                //Cerramos este dialog
                bottomSheetDialogOptionsImagesSelected.dismiss()
                //Quitamos el binding
                bindingBottomSheetDialog = null
                //Quitamos la imagen
                when (resultCodeImageSalected){
                    Constant.RESULT_CODE_CV_IMG_POST_1 -> {
                        if (ConfigThemeApp.isThemeLight(applicationContext))
                            binding.ivImgPost1.setImageResource(R.drawable.ic_camera)
                        else
                            binding.ivImgPost1.setImageResource(R.drawable.ic_camera_dark)
                        binding.ivImgPost1.tag = Constant.TAG_DEFAULT
                        fileImage1 = null
                        photoPath1 = null
                        photoAbsolutePath1 = null
                    }
                    Constant.RESULT_CODE_CV_IMG_POST_2 -> {
                        if (ConfigThemeApp.isThemeLight(applicationContext))
                            binding.ivImgPost2.setImageResource(R.drawable.ic_camera)
                        else
                            binding.ivImgPost2.setImageResource(R.drawable.ic_camera_dark)
                        binding.ivImgPost2.tag = Constant.TAG_DEFAULT
                        fileImage2 = null
                        photoPath2 = null
                        photoAbsolutePath2 = null
                    }
                    Constant.RESULT_CODE_CV_IMG_POST_3 -> {
                        if (ConfigThemeApp.isThemeLight(applicationContext))
                            binding.ivImgPost3.setImageResource(R.drawable.ic_camera)
                        else
                            binding.ivImgPost3.setImageResource(R.drawable.ic_camera_dark)
                        binding.ivImgPost3.tag = Constant.TAG_DEFAULT
                        fileImage3 = null
                        photoPath3 = null
                        photoAbsolutePath3 = null
                    }
                    Constant.RESULT_CODE_CV_IMG_POST_4 -> {
                        if (ConfigThemeApp.isThemeLight(applicationContext))
                            binding.ivImgPost4.setImageResource(R.drawable.ic_camera)
                        else
                            binding.ivImgPost4.setImageResource(R.drawable.ic_camera_dark)
                        binding.ivImgPost4.tag = Constant.TAG_DEFAULT
                        fileImage4 = null
                        photoPath4 = null
                        photoAbsolutePath4 = null
                    }
                }
                toast(getString(R.string.msg_info_imagen_eliminada))
            }
        }
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
                toast(getString(R.string.msg_info_activar_permisos_camara), Toast.LENGTH_LONG)
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
                toast(getString(R.string.msg_info_activar_permisos_galeria), Toast.LENGTH_LONG)
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
    private fun validateFields(): Boolean {

        var imagesUploaded = false
        var titleValid = false
        var descriptionValid = false
        var categoryValid = false
        var priceValid = false

        binding.apply {
            //Validar las imagenes
            if (isUploadedImages()){
                imagesUploaded = true
            } else {
                //Poner error de las imagenes
                mtvErrorImagenes.visibility = View.VISIBLE
            }

            //Validar el titulo
            if (tietTitle.text.toString().trim().isTitleOrDescription()){
                tilTitle.removeError()
                titleValid = true
            } else {
                tietTitle.setText("")
                tilTitle.applyError(getString(R.string.msg_error_title_producto))
            }
            //Validar la descripción
            if (tietDescription.text.toString().trim().isTitleOrDescription()){
                descriptionValid = true
                tilDescription.removeError()
            } else {
                tietDescription.setText("")
                tilDescription.applyError(getString(R.string.msg_error_description_producto))
            }
            //Validar la categoría
            if (tietCategory.text.toString().trim().isNotEmpty()){
                categoryValid = true
                tilCategory.removeError()
            } else {
                tietCategory.setText("")
                tilCategory.applyError(getString(R.string.msg_error_category_producto))
            }
            //Validar el precio
            if (tietPrice.text.toString().isNotEmpty()
                && tietPrice.text.toString()[tietPrice.text.toString().length-1]!='.'
            ){
                priceValid = true
                tilPrice.removeError()
            } else {
                tietPrice.setText("")
                tilPrice.applyError(getString(R.string.msg_error_price_producto))
            }
        }

        return imagesUploaded
                && titleValid
                && descriptionValid
                && categoryValid
                && priceValid
    }

    /**
     * Metodo que valida si hay algun archivo cargado
     * para ser subidos al almacenamiento de firebase
     */
    private fun isUploadedImages(): Boolean {
        if (
            fileImage1.isNull()
            && fileImage2.isNull()
            && fileImage3.isNull()
            && fileImage4.isNull()
        ) {
            return false
        }
        return true
    }

    /**
     * Metodo que publica el producto
     */
    private fun saveProduct() {
        //Construir el producto ya que los campos están validados
        binding.apply {
            product = Product(
                title = tietTitle.text.toString(),
                description = tietDescription.text.toString(),
                category = tietCategory.text.toString(),
                price = tietPrice.text.toString().toDouble(),
                negotiable = tietNegotiable.text.toString(),
                productStatus = tietProductStatus.text.toString(),
                timestamp = Date().time
            )
        }
        //Luego guardamos las imagenes
        saveImages()
    }

    private fun saveImages() {

        //Si la primera imagen no es nula se guarda en firebase
        when {
            fileImage1.isNotNull() -> {
                //Construimos la imagen en bytes
                val imageByte = CompressBitmapImage.getImage(
                    this,
                    fileImage1?.path,
                    Constant.WIDTH_IMAGE_STORAGE,
                    Constant.HEIGHT_IMAGE_STORAGE)
                //y la guardamos
                viewModel.saveImage(imageByte, Constant.RESULT_CODE_CV_IMG_POST_1)
                //Una vez subida se comprueba en el observable si la segunda imagen no es nula
            }
            //De lo contrario si la segunda imagen no es nula se guarda en firebase
            fileImage2.isNotNull() -> {
                //Construimos la imagen en bytes
                val imageByte = CompressBitmapImage.getImage(
                    this,
                    fileImage2?.path,
                    Constant.WIDTH_IMAGE_STORAGE,
                    Constant.HEIGHT_IMAGE_STORAGE)
                //y la guardamos
                viewModel.saveImage(imageByte, Constant.RESULT_CODE_CV_IMG_POST_2)
                //Una vez subida se comprueba en el observable si la tercera imagen no es nula
            }
            //De lo contrario si la tercera imagen no es nula se guarda en firebase
            fileImage3.isNotNull() -> {
                //Construimos la imagen en bytes
                val imageByte = CompressBitmapImage.getImage(
                    this,
                    fileImage3?.path,
                    Constant.WIDTH_IMAGE_STORAGE,
                    Constant.HEIGHT_IMAGE_STORAGE)
                //y la guardamos
                viewModel.saveImage(imageByte, Constant.RESULT_CODE_CV_IMG_POST_3)
                //Una vez subida se comprueba en el observable si la cuarta imagen no es nula
            }
            //De lo contrario si la tercera imagen no es nula se guarda en firebase
            fileImage4.isNotNull() -> {
                //Construimos la imagen en bytes
                val imageByte = CompressBitmapImage.getImage(
                    this,
                    fileImage4?.path,
                    Constant.WIDTH_IMAGE_STORAGE,
                    Constant.HEIGHT_IMAGE_STORAGE)
                //y la guardamos
                viewModel.saveImage(imageByte, Constant.RESULT_CODE_CV_IMG_POST_4)
                //Al ser la ultima ya no se comprobara mas imagenes
            }
        }
    }
}