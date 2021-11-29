package com.app.millennium.ui.activities.product_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.data.model.Like
import com.app.millennium.domain.use_case.likes_db.DeleteLikeUseCase
import com.app.millennium.domain.use_case.likes_db.GetLikeByProductByUserProductByUserSessionUseCase
import com.app.millennium.domain.use_case.likes_db.SaveLikeUseCase
import com.app.millennium.domain.use_case.user_auth.GetIdUseCase
import com.app.millennium.domain.use_case.user_db.GetUserUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.launch

class ProductDetailViewModel : ViewModel() {

    //Caso de uso para obtener el usuario
    private val getUserUseCase = GetUserUseCase()

    //caso de uso para obtener el id del usuario de la sesion
    private val getIdUseCase = GetIdUseCase()

    //Caso de uso para guardar un like
    private val saveLikeUseCase = SaveLikeUseCase()

    //Caso de uso para obtener un like en concreto
    private val getLikeByProductByUserProductByUserSessionUseCase = GetLikeByProductByUserProductByUserSessionUseCase()

    //Caso de uso para eliminar un like
    private val deleteLikeUseCase = DeleteLikeUseCase()

    //Live data obtener usuario
    private val _getUser = MutableLiveData<Task<DocumentSnapshot>>()
    val getUser: LiveData<Task<DocumentSnapshot>> get() = _getUser

    //Live data obtener id usuario de la sesion
    private val _getIdUserSession = MutableLiveData<String>()
    val getIdUserSession: LiveData<String> get() = _getIdUserSession

    //Live data guardar like
    private val _saveLike = MutableLiveData<Task<Void>>()
    val saveLike: LiveData<Task<Void>> get() = _saveLike

    //Live data obtener un like en concreto
    private val _getLikeByProductByUserProductByUserSession = MutableLiveData<Task<QuerySnapshot>>()
    val getLikeByProductByUserProductByUserSession: LiveData<Task<QuerySnapshot>> get() = _getLikeByProductByUserProductByUserSession

    //Live data eliminar un like
    private val _deteleLike = MutableLiveData<Task<Void>>()
    val deteleLike: LiveData<Task<Void>> get() = _deteleLike

    /**
     * Metodo para obtener el usuario mediante el id
     */
    fun getUser(id: String){
        viewModelScope.launch {
            _getUser.postValue(
                getUserUseCase.invoke(id)
            )
        }
    }

    /**
     * Metodo para obtener el id del usuario de la sesion
     */
    fun getIdUserSession(){
        viewModelScope.launch {
            _getIdUserSession.postValue(
                getIdUseCase.invoke()
            )
        }
    }

    /**
     * Metodo que guarda un like
     */
    fun saveLike(like: Like){
        viewModelScope.launch {
            _saveLike.postValue(
                saveLikeUseCase.invoke(like)
            )
        }
    }

    /**
     * Metodo que obtiene un like en concreto
     */
    fun getLikeByProductByUserProductByUserSession(like: Like){
        viewModelScope.launch {
            _getLikeByProductByUserProductByUserSession.postValue(
                getLikeByProductByUserProductByUserSessionUseCase.invoke(like)
            )
        }
    }

    /**
     * Metodo que elimina un like
     */
    fun deleteLike(id: String){
        viewModelScope.launch {
            _deteleLike.postValue(
                deleteLikeUseCase.invoke(id)
            )
        }
    }
}