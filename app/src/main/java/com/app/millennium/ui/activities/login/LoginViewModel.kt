package com.app.millennium.ui.activities.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.domain.use_case.user_auth.SignInEmailAndPasswordUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val signInEmailAndPasswordUseCase = SignInEmailAndPasswordUseCase()

    private val _signInWithEmailAndPassword = MutableLiveData<Task<AuthResult>>()
    val signInWithEmailAndPassword: LiveData<Task<AuthResult>> get() = _signInWithEmailAndPassword

    fun signInEmailPassword(email: String, password: String){

        viewModelScope.launch {
            _signInWithEmailAndPassword.postValue(
                signInEmailAndPasswordUseCase.invoke(email, password)
            )
        }
    }

}