package com.app.millennium.ui.activities.edit_profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import com.app.millennium.R
import com.app.millennium.core.common.*
import com.app.millennium.core.utils.CompressBitmapImage
import com.app.millennium.core.utils.ConfigThemeApp
import com.app.millennium.core.utils.FileUtil
import com.app.millennium.databinding.ActivityEditProfileBinding
import com.app.millennium.databinding.ViewBottomSheetOptionsImagesSelectedBinding
import com.app.millennium.databinding.ViewBottomSheetOptionsSourceImagesBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import java.io.File
import java.util.*

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private val viewModel: EditProfileViewModel by viewModels()

    private lateinit var bundle: Bundle

    //Ficheros para almacenar las imagenes
    private var fileImageCover: File? = null
    private var fileImageProfile: File? = null

    //Paths de las imagenes de la camara
    private var photoPathCover: String? = null
    private var photoPathProfile: String? = null

    //AbsolutePaths de las imagenes de la camara
    private var photoAbsolutePathCover: String? = null
    private var photoAbsolutePathProfile: String? = null

    /*
     * Variable para obtener un flag para saber siempre qué
     * input de las imágenes se ha pulsado
     */
    private var resultCodeImageSalected: Int = 0
    //AlertDialog
    private lateinit var dialogLoading: android.app.AlertDialog

    /**
     * Launcher para abrir la camara y tener la lógica
     * para saber desde que input de las imagenes ha
     * sido lanzada.
     */
    private val launcherCameraImageCover =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            if (it.resultCode == RESULT_OK){
                fileImageCover = File(photoAbsolutePathCover!!)
                Picasso.get().load(photoPathCover).into(
                    binding.ivCover
                )
                binding.ivCover.scaleType = ImageView.ScaleType.CENTER_CROP
                binding.ivCover.tag = Constant.TAG_NOT_DEFAULT
            }
        }

    private val launcherCameraImageProfile =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            if (it.resultCode == RESULT_OK){
                fileImageProfile = File(photoAbsolutePathProfile!!)
                Picasso.get().load(photoPathProfile).into(
                    binding.civImageProfile
                )
                binding.civImageProfile.tag = Constant.TAG_NOT_DEFAULT
            }
        }

    /**
     * Launcher para abrir la galería y tener la lógica
     * para saber desde que input de las imagenes ha
     * sido lanzada.
     */
    private val launcherGalleryImageCover =
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
                    fileImageCover = FileUtil.from(
                        this,
                        it.data?.data!!
                    )
                    /**
                     * A continuación verificamos si la extension
                     * del fichero es permitido porque puede ser
                     * que el usuario elija un archivo que no sea una
                     * imagen (formatos permitidos = JPEG, PNG, JPG)
                     */
                    if (fileImageCover!!.permitted()){
                        //Si el formato es permitido entonces:

                        /**
                         * Le seteamos la imagen al input del que se haya
                         * pulsado para abrir la galeria
                         */
                        binding.ivCover.setImageBitmap(
                            BitmapFactory.decodeFile(
                                fileImageCover?.absolutePath
                            )
                        )
                        /**
                         * Le seteamos el tag al imageview donde
                         * ha sido establecida la imagen para
                         * saber siempre si el input tiene una imagen
                         * o no la tiene
                         */
                        binding.ivCover.tag = Constant.TAG_NOT_DEFAULT
                    } else {
                        toast(getString(R.string.msg_error_archivo_no_permitido))
                        fileImageCover = null
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

    private val launcherGalleryImageProfile =
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
                    fileImageProfile = FileUtil.from(
                        this,
                        it.data?.data!!
                    )
                    /**
                     * A continuación verificamos si la extension
                     * del fichero es permitido porque puede ser
                     * que el usuario elija un archivo que no sea una
                     * imagen (formatos permitidos = JPEG, PNG, JPG)
                     */
                    if (fileImageProfile!!.permitted()){
                        //Si el formato es permitido entonces:

                        /**
                         * Le seteamos la imagen al input del que se haya
                         * pulsado para abrir la galeria
                         */
                        binding.civImageProfile.setImageBitmap(
                            BitmapFactory.decodeFile(
                                fileImageProfile?.absolutePath
                            )
                        )
                        /**
                         * Le seteamos el tag al imageview donde
                         * ha sido establecida la imagen para
                         * saber siempre si el input tiene una imagen
                         * o no la tiene
                         */
                        binding.civImageProfile.tag = Constant.TAG_NOT_DEFAULT
                    } else {
                        toast(getString(R.string.msg_error_archivo_no_permitido))
                        fileImageProfile = null
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
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Obtener bundle
        bundle = intent.getBundleExtra(Constant.BUNDLE_USER)!!

        initUI()
        initObservables()
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
    
    private fun initUI(){
        configComponents() //Setear info del bundle a los componentes de la vista
        configOnClickComponents()
    }

    private fun initObservables() {

        viewModel.apply {

            //Cuando se guarde el cover entonces
            saveImageCover.observe(
                this@EditProfileActivity,
                {
                    it.addOnCompleteListener{ task ->
                        if (task.isSuccessful){
                            viewModel.getUrlImage(Constant.RESULT_CODE_CV_IMG_POST_COVER)
                        }
                    }
                    it.addOnFailureListener{ exc ->
                        toast("${exc.message}")
                        dialogLoading.dismiss()
                    }
                }
            )

            //Cuando se guarde el profile entonces
            saveImageProfile.observe(
                this@EditProfileActivity,
                {
                    it.addOnCompleteListener{ task ->
                        if (task.isSuccessful){
                            viewModel.getUrlImage(Constant.RESULT_CODE_CV_IMG_POST_PROFILE)
                        }
                    }
                    it.addOnFailureListener{ exc ->
                        toast("${exc.message}")
                        dialogLoading.dismiss()
                    }
                }
            )

            //Cuando se obtenga la url del cover entonces
            getUrlImageCover.observe(
                this@EditProfileActivity,
                {
                    it?.let { task ->
                        task.addOnSuccessListener { uri ->
                            uri?.let { _uri ->
                                viewModel.updateImgCover(
                                    bundle[Constant.PROP_ID_USER].toString(),
                                    _uri.toString()
                                )
                            }
                        }
                        task.addOnFailureListener { exc ->
                            toast("${exc.message}")
                            dialogLoading.dismiss()
                        }
                    }
                }
            )

            //Cuando se obtenga la url del profile entonces
            getUrlImageProfile.observe(
                this@EditProfileActivity,
                {
                    it?.let { task ->
                        task.addOnSuccessListener { uri ->
                            uri?.let { _uri ->
                                viewModel.updateImgProfile(
                                    bundle[Constant.PROP_ID_USER].toString(),
                                    _uri.toString()
                                )
                            }
                        }
                        task.addOnFailureListener { exc ->
                            toast("${exc.message}")
                            dialogLoading.dismiss()
                        }
                    }
                }
            )

            //Cuando se actualice el cover en el user entonces
            updateImgCoverUser.observe(
                this@EditProfileActivity,
                {
                    it?.let { task ->
                        task.addOnCompleteListener { _task ->
                            if (_task.isSuccessful){

                                //Ahora preguntamos si la imagen del perfil es establecida
                                when {
                                    fileImageProfile.isNotNull() -> {
                                        //Construimos la imagen en bytes
                                        val imageByte = CompressBitmapImage.getImage(
                                            this@EditProfileActivity,
                                            fileImageProfile?.path,
                                            Constant.WIDTH_IMAGE_STORAGE,
                                            Constant.HEIGHT_IMAGE_STORAGE
                                        )
                                        //y la guardamos
                                        viewModel.saveImage(
                                            imageByte,
                                            Constant.RESULT_CODE_CV_IMG_POST_PROFILE
                                        )
                                    }
                                    binding.tietUsername.text.toString() != bundle[Constant.PROP_USERNAME_USER] -> {
                                        viewModel.updateName(
                                            bundle[Constant.PROP_ID_USER].toString(),
                                            binding.tietUsername.text.toString()
                                        )
                                    }
                                    binding.tietPhone.text.toString() != bundle[Constant.PROP_PHONE_USER].toString() -> {
                                        viewModel.updatePhone(
                                            bundle[Constant.PROP_ID_USER].toString(),
                                            binding.tietPhone.text.toString()
                                        )
                                    }
                                    else -> {
                                        dialogLoading.dismiss()
                                        toast(getString(R.string.msg_info_cambios_aplicados))
                                        finishAndRemoveTask()
                                    }
                                }
                            }
                        }
                        task.addOnFailureListener { exc ->
                            toast("${exc.message}")
                            dialogLoading.dismiss()
                        }
                    }
                }
            )

            //Cuando se actualice el profile en el user entonces
            updateImgProfileUser.observe(
                this@EditProfileActivity,
                {
                    it?.let { task ->
                        task.addOnCompleteListener { _task ->
                            if (_task.isSuccessful){
                                //Si se ha actualizado entonces preguntamos si el nombre también ha sido cambiado
                                when {
                                    binding.tietUsername.text.toString() != bundle[Constant.PROP_USERNAME_USER] -> {
                                        viewModel.updateName(
                                            bundle[Constant.PROP_ID_USER].toString(),
                                            binding.tietUsername.text.toString()
                                        )
                                    }
                                    //De lo contrario preguntamos si el telefono se ha cambiado
                                    binding.tietPhone.text.toString() != bundle[Constant.PROP_PHONE_USER].toString() -> {
                                        viewModel.updatePhone(
                                            bundle[Constant.PROP_ID_USER].toString(),
                                            binding.tietPhone.text.toString()
                                        )
                                    }
                                    else -> {
                                        dialogLoading.dismiss()
                                        toast(getString(R.string.msg_info_cambios_aplicados))
                                        finishAndRemoveTask()
                                    }
                                }
                            }
                        }
                        task.addOnFailureListener { exc ->
                            toast("${exc.message}")
                            dialogLoading.dismiss()
                        }
                    }
                }
            )

            //Cuando se actualice el name en el user entonces
            updateNameUser.observe(
                this@EditProfileActivity,
                {
                    it?.let { task ->
                        task.addOnCompleteListener { _task ->
                            if (_task.isSuccessful){
                                //Si el nombre se ha actualizado entonces preguntamos si el telefono
                                //tambien ha sido modificado
                                if (binding.tietPhone.text.toString() != bundle[Constant.PROP_PHONE_USER].toString()
                                ) {
                                    viewModel.updatePhone(
                                        bundle[Constant.PROP_ID_USER].toString(),
                                        binding.tietPhone.text.toString()
                                    )
                                } else {
                                    dialogLoading.dismiss()
                                    toast(getString(R.string.msg_info_cambios_aplicados))
                                    finishAndRemoveTask()
                                }
                            }
                        }
                        task.addOnFailureListener { exc ->
                            toast("${exc.message}")
                            dialogLoading.dismiss()
                        }
                    }
                }
            )

            //Cuando se actualice el phone en el user entonces
            updatePhoneUser.observe(
                this@EditProfileActivity,
                {
                    it?.let { task ->
                        task.addOnCompleteListener { _task ->
                            dialogLoading.dismiss()
                            if (_task.isSuccessful){
                                //Como ya no hay más cambios entonces volvemos al fragment del perfil
                                toast(getString(R.string.msg_info_cambios_aplicados))
                                finishAndRemoveTask()
                            }
                        }
                        task.addOnFailureListener { exc ->
                            toast("${exc.message}")
                            dialogLoading.dismiss()
                        }
                    }
                }
            )
        }
    }

    private fun configOnClickComponents() {
        configImages()

        binding.apply {

            //btn iv_back para cerrar la activity
            ivBack.setOnClickListener {
                finish()
            }

            //btnConfirm para guardar los cambios establecidos
            btnConfirm.setOnClickListener {
                this@EditProfileActivity.reload()
                if (validateInputs(
                        tietUsername.text.toString().trim(),
                        tietPhone.text.toString().trim()
                )){
                    dialogLoading.show()
                    saveChanges()
                }
            }
        }
    }

    /**
     * Metodo que setea todos los campos de la ui
     */
    private fun configComponents() {

        dialogLoading = SpotsDialog
            .Builder()
            .setMessage(getString(R.string.msg_alert_aplicando_cambios))
            .setContext(this)
            .setCancelable(false)
            .build()

        binding.apply {

            //Username
            tietUsername.setText(bundle[Constant.PROP_USERNAME_USER].toString())

            //Phone
            if (bundle[Constant.PROP_PHONE_USER].isNotNull())
                tietPhone.setText(bundle[Constant.PROP_PHONE_USER].toString())


            //ImgCover
            if (bundle[Constant.PROP_IMG_COVER_USER].isNull()){
                ivCover.scaleType = ImageView.ScaleType.CENTER_INSIDE
                if (ConfigThemeApp.isThemeLight(applicationContext)){
                    ivCover.setImageResource(R.drawable.ic_camera)
                } else {
                    ivCover.setImageResource(R.drawable.ic_camera_dark)
                }
                ivCover.tag = Constant.TAG_DEFAULT
            } else {
                Picasso.get().load(bundle[Constant.PROP_IMG_COVER_USER].toString()).into(ivCover)
                ivCover.scaleType = ImageView.ScaleType.CENTER_CROP
                ivCover.tag = Constant.TAG_NOT_DEFAULT
                //Pero no se guarda el fichero porque no se ha modificado por ello no se
                //debe guardar nuevamente en la base de datos
            }

            //ImgProfile
            if (bundle[Constant.PROP_IMG_PROFILE_USER].isNull()){
                civImageProfile.setImageResource(R.drawable.ic_user_profile)
                civImageProfile.tag = Constant.TAG_DEFAULT
            } else {
                Picasso.get().load(bundle[Constant.PROP_IMG_PROFILE_USER].toString()).into(civImageProfile)
                civImageProfile.tag = Constant.TAG_NOT_DEFAULT
                //Pero no se guarda el fichero porque no se ha modificado por ello no se
                //debe guardar nuevamente en la base de datos
            }
        }
    }

    /**
     * Metodo que configura la selección de las imagenes
     */
    private fun configImages() {

        binding.apply {

            ivCover.setOnClickListener {
                this@EditProfileActivity.reload()
                resultCodeImageSalected = Constant.RESULT_CODE_CV_IMG_POST_COVER
                configBottomSheetOption()
            }
            civImageProfile.setOnClickListener {
                this@EditProfileActivity.reload()
                resultCodeImageSalected = Constant.RESULT_CODE_CV_IMG_POST_PROFILE
                configBottomSheetOption()
            }
        }
    }

    /**
     * Metodo para guardar los cambios
     */
    private fun saveChanges() {
        binding.apply {

            when {
                //Imagen cover
                fileImageCover.isNotNull() -> {
                    //Construimos la imagen en bytes
                    val imageByte = CompressBitmapImage.getImage(
                        this@EditProfileActivity,
                        fileImageCover?.path,
                        Constant.WIDTH_IMAGE_STORAGE,
                        Constant.HEIGHT_IMAGE_STORAGE)
                    //y la guardamos
                    viewModel.saveImage(imageByte, Constant.RESULT_CODE_CV_IMG_POST_COVER)
                }

                //Imaen Profile
                fileImageProfile.isNotNull() -> {
                    //Construimos la imagen en bytes
                    val imageByte = CompressBitmapImage.getImage(
                        this@EditProfileActivity,
                        fileImageProfile?.path,
                        Constant.WIDTH_IMAGE_STORAGE,
                        Constant.HEIGHT_IMAGE_STORAGE
                    )
                    //y la guardamos
                    viewModel.saveImage(imageByte, Constant.RESULT_CODE_CV_IMG_POST_PROFILE)
                }

                //Field name User
                tietUsername.text.toString() != bundle[Constant.PROP_USERNAME_USER] -> {
                    viewModel.updateName(
                        bundle[Constant.PROP_ID_USER].toString(),
                        tietUsername.text.toString()
                    )
                }

                //Field phone User
                tietPhone.text.toString() != bundle[Constant.PROP_PHONE_USER].toString() -> {
                    viewModel.updatePhone(
                        bundle[Constant.PROP_ID_USER].toString(),
                        tietPhone.text.toString()
                    )
                }

                else -> {
                    dialogLoading.dismiss()
                    toast(getString(R.string.msg_info_cambios_aplicados))
                    finishAndRemoveTask()
                }
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
            Constant.RESULT_CODE_CV_IMG_POST_COVER -> {
                //Ahora debemos comprobar si tiene una imagen o no tiene una imagen
                //Verificando que el tag sea default
                //Si es default significa que no tiene ninguna imagen
                if (binding.ivCover.tag.equals(Constant.TAG_DEFAULT)){
                    openBottomSheetDialogOptionsCameraOrGallery()
                } else {
                    openBottomSheetDialogOptionsEditOrDelete()
                }
            }
            Constant.RESULT_CODE_CV_IMG_POST_PROFILE -> {
                if (binding.civImageProfile.tag.equals(Constant.TAG_DEFAULT)){
                    openBottomSheetDialogOptionsCameraOrGallery()
                } else {
                    openBottomSheetDialogOptionsEditOrDelete()
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
                    Constant.RESULT_CODE_CV_IMG_POST_COVER -> {

                        if (ConfigThemeApp.isThemeLight(applicationContext))
                            binding.ivCover.setImageResource(R.drawable.ic_camera)
                        else
                            binding.ivCover.setImageResource(R.drawable.ic_camera_dark)

                        binding.ivCover.scaleType = ImageView.ScaleType.CENTER_INSIDE
                        binding.ivCover.tag = Constant.TAG_DEFAULT
                        fileImageCover = null
                        photoPathCover = null
                        photoAbsolutePathCover = null
                    }
                    Constant.RESULT_CODE_CV_IMG_POST_PROFILE -> {

                        binding.civImageProfile.setImageResource(R.drawable.ic_user_profile)

                        binding.civImageProfile.tag = Constant.TAG_DEFAULT
                        fileImageProfile = null
                        photoPathProfile = null
                        photoAbsolutePathProfile = null
                    }
                }
                toast(getString(R.string.msg_info_imagen_eliminada))
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
            Constant.RESULT_CODE_CV_IMG_POST_COVER -> {
                launcherCameraImageCover.launch(camera)
            }
            Constant.RESULT_CODE_CV_IMG_POST_PROFILE -> {
                launcherCameraImageProfile.launch(camera)
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
        return when (resultCodeImageSalected){
            Constant.RESULT_CODE_CV_IMG_POST_COVER -> {
                photoPathCover = "file:${filePhoto.absolutePath}"
                photoAbsolutePathCover = filePhoto.absolutePath
                filePhoto
            }
            Constant.RESULT_CODE_CV_IMG_POST_PROFILE -> {
                photoPathProfile = "file:${filePhoto.absolutePath}"
                photoAbsolutePathProfile = filePhoto.absolutePath
                filePhoto
            }
            else -> {
                null
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
            Constant.RESULT_CODE_CV_IMG_POST_COVER -> {
                launcherGalleryImageCover.launch(gallery)
            }
            Constant.RESULT_CODE_CV_IMG_POST_PROFILE -> {
                launcherGalleryImageProfile.launch(gallery)
            }
        }
    }

    /**
     * Metodo para validar los campos, devuelve true
     * si todos los campos son correctos y devuelve
     * false de todo lo contrario
     */
    private fun validateInputs(
        username: String,
        phone: String
    ): Boolean {
        var usernameValid = false
        var phoneValid = false

        binding.apply {

            //Validando el username
            if (username.isUsername()){
                usernameValid = true
                tilUsername.removeError()
            } else {
                tietUsername.setText(bundle[Constant.PROP_USERNAME_USER].toString())
                tilUsername.applyError(getString(R.string.msg_error_username))
            }

            //Validando el telefono
            //Solo se validará si se ha introducido dígitos
            if (phone.isNotEmpty()){
                if (phone.isPhone()){
                    phoneValid = true
                    tilPhone.removeError()
                } else {
                    if (bundle[Constant.PROP_PHONE_USER].isNotNull()){
                        tietPhone.setText(bundle[Constant.PROP_PHONE_USER].toString())
                    } else {
                        tietPhone.setText("")
                    }
                    tilPhone.applyError(getString(R.string.msg_error_phone))
                }
            } else{
                phoneValid = true
                tilPhone.removeError()
            }
        }

        return usernameValid && phoneValid
    }
}