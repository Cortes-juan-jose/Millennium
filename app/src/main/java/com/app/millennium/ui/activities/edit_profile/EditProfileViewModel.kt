package com.app.millennium.ui.activities.edit_profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.core.common.Constant
import com.app.millennium.domain.use_case.images_storage.GetUrlImageUseCase
import com.app.millennium.domain.use_case.images_storage.SaveImageUseCase
import com.app.millennium.domain.use_case.user_db.UpdateImgCoverUserUseCase
import com.app.millennium.domain.use_case.user_db.UpdateImgProfileUserUseCase
import com.app.millennium.domain.use_case.user_db.UpdateNameUserUseCase
import com.app.millennium.domain.use_case.user_db.UpdatePhoneUserUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.launch

class EditProfileViewModel : ViewModel() {

    //Caso de uso guardar imagen
    private val saveImageUseCase = SaveImageUseCase()
    //Caso de uso para obtener la url de las imagenes almacenadas
    private val getUrlImageUseCase = GetUrlImageUseCase()
    //Caso de uso para actualizar imgCover
    private val updateImgCoverUserUseCase = UpdateImgCoverUserUseCase()
    //Caso de uso para actualizar imgProfile
    private val updateImgProfileUserUseCase = UpdateImgProfileUserUseCase()
    //Caso de uso para actualizar name
    private val updateNameUserUseCase = UpdateNameUserUseCase()
    //Caso de uso para actualizar phone
    private val updatePhoneUserUseCase = UpdatePhoneUserUseCase()

    //Live data guardar imagen
    private val _saveImageCover = MutableLiveData<UploadTask>()
    val saveImageCover: LiveData<UploadTask> get() = _saveImageCover

    private val _saveImageProfile = MutableLiveData<UploadTask>()
    val saveImageProfile: LiveData<UploadTask> get() = _saveImageProfile

    //Live data obtener la url imagen
    private val _getUrlImageCover = MutableLiveData<Task<Uri>?>()
    val getUrlImageCover: LiveData<Task<Uri>?> get() = _getUrlImageCover

    private val _getUrlImageProfile = MutableLiveData<Task<Uri>?>()
    val getUrlImageProfile: LiveData<Task<Uri>?> get() = _getUrlImageProfile

    //Live data actualizar user
    private val _updateImgCoverUser = MutableLiveData<Task<Void>>()
    val updateImgCoverUser: LiveData<Task<Void>> get() = _updateImgCoverUser

    private val _updateImgProfileUser = MutableLiveData<Task<Void>>()
    val updateImgProfileUser: LiveData<Task<Void>> get() = _updateImgProfileUser

    private val _updateNameUser = MutableLiveData<Task<Void>>()
    val updateNameUser: LiveData<Task<Void>> get() = _updateNameUser

    private val _updatePhoneUser = MutableLiveData<Task<Void>>()
    val updatePhoneUser: LiveData<Task<Void>> get() = _updatePhoneUser



    //Metodo guardar imagen
    fun saveImage(imageByteArray: ByteArray, resultCodeImageSalected: Int) {

        when (resultCodeImageSalected){
            Constant.RESULT_CODE_CV_IMG_POST_COVER -> {
                viewModelScope.launch {
                    _saveImageCover.postValue(
                        saveImageUseCase.invoke(imageByteArray)
                    )
                }
            }
            Constant.RESULT_CODE_CV_IMG_POST_PROFILE -> {
                viewModelScope.launch {
                    _saveImageProfile.postValue(
                        saveImageUseCase.invoke(imageByteArray)
                    )
                }
            }
        }
    }

    //Metodo obtener url imagen
    fun getUrlImage(resultCodeImageSalected: Int) {

        when (resultCodeImageSalected) {
            Constant.RESULT_CODE_CV_IMG_POST_COVER -> {
                viewModelScope.launch {
                    _getUrlImageCover.postValue(
                        getUrlImageUseCase.invoke()
                    )
                }
            }
            Constant.RESULT_CODE_CV_IMG_POST_PROFILE -> {
                viewModelScope.launch {
                    _getUrlImageProfile.postValue(
                        getUrlImageUseCase.invoke()
                    )
                }
            }
        }
    }

    //Metodo actualizar imgCover
    fun updateImgCover(id: String, newValue: String){
        viewModelScope.launch {
            _updateImgCoverUser.postValue(
                updateImgCoverUserUseCase.invoke(id, newValue)
            )
        }
    }

    //Metodo actualizar imgProfile
    fun updateImgProfile(id: String, newValue: String){
        viewModelScope.launch {
            _updateImgProfileUser.postValue(
                updateImgProfileUserUseCase.invoke(id, newValue)
            )
        }
    }

    //Metodo actualizar name
    fun updateName(id: String, newValue: String){
        viewModelScope.launch {
            _updateNameUser.postValue(
                updateNameUserUseCase.invoke(id, newValue)
            )
        }
    }

    //Metodo actualizar phone
    fun updatePhone(id: String, newValue: String){
        viewModelScope.launch {
            _updatePhoneUser.postValue(
                updatePhoneUserUseCase.invoke(id, newValue)
            )
        }
    }

}