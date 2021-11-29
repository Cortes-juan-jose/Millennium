package com.app.millennium.ui.adapters.product_profile_user_to_product_selected

import com.app.millennium.domain.use_case.likes_db.DeleteLikeUseCase
import com.app.millennium.domain.use_case.likes_db.GetAllLikeByUserUseCase
import com.app.millennium.domain.use_case.likes_db.GetLikeByProductByUserProductByUserSessionUseCase
import com.app.millennium.domain.use_case.likes_db.SaveLikeUseCase
import com.app.millennium.domain.use_case.product_db.DeleteProductUseCase
import com.app.millennium.domain.use_case.user_auth.GetIdUseCase
import com.app.millennium.domain.use_case.user_db.GetUserUseCase
import com.app.millennium.domain.use_case.user_db.UpdateUploadedProductsUserUseCase

interface ProductProfileUserToProductSelectedUsesCases {
    val saveLikeUseCase: SaveLikeUseCase
    val getAllLikeByUserUseCase: GetAllLikeByUserUseCase
    val getLikeByProductByUserProductByUserSessionUseCase: GetLikeByProductByUserProductByUserSessionUseCase
    val getIdUseCase: GetIdUseCase
    val deleteLikeUseCase: DeleteLikeUseCase

}