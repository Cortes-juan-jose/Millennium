package com.app.millennium.ui.activities.post_product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.core.common.Constant
import com.app.millennium.domain.use_case.images_storage.SaveImageUseCase
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.launch

class PostProductViewModel: ViewModel() {

    //Caso de uso guardar imagen
    private val saveImageUseCase = SaveImageUseCase()

    //Live data guardar imagen
    private val _saveImage1 = MutableLiveData<UploadTask>()
    val saveImage1: LiveData<UploadTask> get() = _saveImage1

    private val _saveImage2 = MutableLiveData<UploadTask>()
    val saveImage2: LiveData<UploadTask> get() = _saveImage2

    private val _saveImage3 = MutableLiveData<UploadTask>()
    val saveImage3: LiveData<UploadTask> get() = _saveImage3

    private val _saveImage4 = MutableLiveData<UploadTask>()
    val saveImage4: LiveData<UploadTask> get() = _saveImage4

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
}