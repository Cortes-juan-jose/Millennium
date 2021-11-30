package com.app.millennium.ui.adapters.chat

import com.app.millennium.domain.use_case.user_auth.GetIdUseCase
import com.app.millennium.domain.use_case.user_db.GetUserUseCase

interface ChatUsesCases {

    val getIdUseCase: GetIdUseCase
    val getUserUseCase: GetUserUseCase
}