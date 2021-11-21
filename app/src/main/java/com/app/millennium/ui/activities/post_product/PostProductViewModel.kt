package com.app.millennium.ui.activities.post_product

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.core.common.Constant
import com.app.millennium.data.model.Product
import com.app.millennium.data.model.User
import com.app.millennium.domain.use_case.images_storage.GetUrlImageUseCase
import com.app.millennium.domain.use_case.images_storage.SaveImageUseCase
import com.app.millennium.domain.use_case.product_db.SaveProductUseCase
import com.app.millennium.domain.use_case.user_auth.GetIdUseCase
import com.app.millennium.domain.use_case.user_db.GetUserUseCase
import com.app.millennium.domain.use_case.user_db.UpdateUploadedProductsUserUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.launch

class PostProductViewModel: ViewModel() {

    //Caso de uso guardar imagen
    private val saveImageUseCase = SaveImageUseCase()
    //Caso de uso guardar producto
    private val saveProductUseCase = SaveProductUseCase()
    //Caso de uso para obtener la url de las imagenes almacenadas
    private val getUrlImageUseCase = GetUrlImageUseCase()
    //Caso de uso para obtener el id del usuario
    private val getIdUserUseCase = GetIdUseCase()
    //Caso de uso para actualizar el campo uploadedProducts del usuario
    private val updateUploadedProductsUserUseCase = UpdateUploadedProductsUserUseCase()
    //Caso de uso para obtener el usuario que publica el producto
    private val getUserUseCase = GetUserUseCase()

    //Live data guardar imagen
    private val _saveImage1 = MutableLiveData<UploadTask>()
    val saveImage1: LiveData<UploadTask> get() = _saveImage1

    private val _saveImage2 = MutableLiveData<UploadTask>()
    val saveImage2: LiveData<UploadTask> get() = _saveImage2

    private val _saveImage3 = MutableLiveData<UploadTask>()
    val saveImage3: LiveData<UploadTask> get() = _saveImage3

    private val _saveImage4 = MutableLiveData<UploadTask>()
    val saveImage4: LiveData<UploadTask> get() = _saveImage4

    //Live data guardar producto
    private val _saveProduct = MutableLiveData<Task<Void>>()
    val saveProduct: LiveData<Task<Void>> get() = _saveProduct

    //Live data obtener la url imagen
    private val _getUrlImage1 = MutableLiveData<Task<Uri>?>()
    val getUrlImage1: LiveData<Task<Uri>?> get() = _getUrlImage1

    private val _getUrlImage2 = MutableLiveData<Task<Uri>?>()
    val getUrlImage2: LiveData<Task<Uri>?> get() = _getUrlImage2

    private val _getUrlImage3 = MutableLiveData<Task<Uri>?>()
    val getUrlImage3: LiveData<Task<Uri>?> get() = _getUrlImage3

    private val _getUrlImage4 = MutableLiveData<Task<Uri>?>()
    val getUrlImage4: LiveData<Task<Uri>?> get() = _getUrlImage4

    //Live data obtener id usuario
    private val _getIdUser = MutableLiveData<String>()
    val getIdUser: LiveData<String> get() = _getIdUser

    //Live data actualizar uploadedProducts del usuario
    private val _updateUploadedProducts = MutableLiveData<Task<Void>>()
    val updateUploadedProducts: LiveData<Task<Void>> get() = _updateUploadedProducts
    
    //Live data obtener usuario que ha publicdo el producto
    private val _getUser = MutableLiveData<Task<DocumentSnapshot>>()
    val getUser: LiveData<Task<DocumentSnapshot>> get() = _getUser

    //Metodo guardar imagen
    fun saveImage(imageByteArray: ByteArray, resultCodeImageSalected: Int) {

        when (resultCodeImageSalected){
            Constant.RESULT_CODE_CV_IMG_POST_1 -> {
                viewModelScope.launch {
                    _saveImage1.postValue(
                        saveImageUseCase.invoke(imageByteArray)
                    )
                }
            }
            Constant.RESULT_CODE_CV_IMG_POST_2 -> {
                viewModelScope.launch {
                    _saveImage2.postValue(
                        saveImageUseCase.invoke(imageByteArray)
                    )
                }
            }
            Constant.RESULT_CODE_CV_IMG_POST_3 -> {
                viewModelScope.launch {
                    _saveImage3.postValue(
                        saveImageUseCase.invoke(imageByteArray)
                    )
                }
            }
            Constant.RESULT_CODE_CV_IMG_POST_4 -> {
                viewModelScope.launch {
                    _saveImage4.postValue(
                        saveImageUseCase.invoke(imageByteArray)
                    )
                }
            }
        }
    }

    //Metodo obtener url imagen
    fun getUrlImage(resultCodeImageSalected: Int) {

        when (resultCodeImageSalected){
            Constant.RESULT_CODE_CV_IMG_POST_1 -> {
                viewModelScope.launch {
                    _getUrlImage1.postValue(
                        getUrlImageUseCase.invoke()
                    )
                }
            }
            Constant.RESULT_CODE_CV_IMG_POST_2 -> {
                viewModelScope.launch {
                    _getUrlImage2.postValue(
                        getUrlImageUseCase.invoke()
                    )
                }
            }
            Constant.RESULT_CODE_CV_IMG_POST_3 -> {
                viewModelScope.launch {
                    _getUrlImage3.postValue(
                        getUrlImageUseCase.invoke()
                    )
                }
            }
            Constant.RESULT_CODE_CV_IMG_POST_4 -> {
                viewModelScope.launch {
                    _getUrlImage4.postValue(
                        getUrlImageUseCase.invoke()
                    )
                }
            }
        }
    }

    //Metodo guardar producto
    fun saveProduct(product: Product) {

        viewModelScope.launch {
            _saveProduct.postValue(
                saveProductUseCase.invoke(product)
            )
        }
    }

     //Funcion para obtener el id de inicio de sesion
    fun getIdUser(){
        viewModelScope.launch {
            _getIdUser.postValue(
                getIdUserUseCase.invoke()
            )
        }
    }

    //Metodo para actualizar el campo uploadedProducts del usuario
    fun updateUploadedProducts(user: User){
        viewModelScope.launch {
            _updateUploadedProducts.postValue(
                updateUploadedProductsUserUseCase.invoke(user)
            )
        }
    }

    //Metodo para obtener el usuario que ha publiado el producto
    fun getUser(id: String){
        viewModelScope.launch {
            _getUser.postValue(
                getUserUseCase.invoke(id)
            )
        }
    }

}