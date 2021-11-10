package com.app.millennium.ui.activities.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.domain.use_case.user_auth.CreateAccountUseCase
import com.app.millennium.domain.use_case.user_auth.SignInEmailAndPasswordUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val createAccountUseCase = CreateAccountUseCase()
    private val signInEmailAndPasswordUseCase = SignInEmailAndPasswordUseCase()

    private val _createAccount = MutableLiveData<Task<AuthResult>>()
    val createAccount: LiveData<Task<AuthResult>> get() = _createAccount

    private val _signInWithEmailAndPassword = MutableLiveData<Task<AuthResult>>()
    val signInWithEmailAndPassword: LiveData<Task<AuthResult>> get() = _signInWithEmailAndPassword



    fun createAccount(email: String, password: String){
        viewModelScope.launch {
            _createAccount.postValue(
                createAccountUseCase.invoke(email, password)
            )
        }
    }

    fun signInWithEmailAndPassword(email: String, password: String){

        viewModelScope.launch {
            _signInWithEmailAndPassword.postValue(
                signInEmailAndPasswordUseCase.invoke(email, password)
            )
        }
    }

}