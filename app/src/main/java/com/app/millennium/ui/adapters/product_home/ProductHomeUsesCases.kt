package com.app.millennium.ui.adapters.product_home

import com.app.millennium.domain.use_case.likes_db.DeleteLikeUseCase
import com.app.millennium.domain.use_case.likes_db.GetAllLikeByUserUseCase
import com.app.millennium.domain.use_case.likes_db.GetLikeByProductByUserProductByUserSessionUseCase
import com.app.millennium.domain.use_case.likes_db.SaveLikeUseCase
import com.app.millennium.domain.use_case.user_auth.GetIdUseCase

interface ProductHomeUsesCases {
    val saveLikeUseCase: SaveLikeUseCase
    val getAllLikeByUserUseCase: GetAllLikeByUserUseCase
    val getLikeByProductByUserProductByUserSessionUseCase: GetLikeByProductByUserProductByUserSessionUseCase
    val getIdUseCase: GetIdUseCase
    val deleteLikeUseCase: DeleteLikeUseCase
}