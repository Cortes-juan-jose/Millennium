package com.app.millennium.ui.fragments.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.millennium.domain.use_case.user_auth.SignOutUseCase
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {

    private val signOutUseCase = SignOutUseCase()

    fun signOut(){
        viewModelScope.launch {
            signOutUseCase.invoke()
        }
    }
}