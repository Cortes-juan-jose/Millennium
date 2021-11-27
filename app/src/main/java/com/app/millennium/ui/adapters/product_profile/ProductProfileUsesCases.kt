package com.app.millennium.ui.adapters.product_profile

import com.app.millennium.domain.use_case.product_db.DeleteProductUseCase
import com.app.millennium.domain.use_case.user_db.GetUserUseCase
import com.app.millennium.domain.use_case.user_db.UpdateUploadedProductsUserUseCase

interface ProductProfileUsesCases {
    val deleteProductUseCase: DeleteProductUseCase
    val getUserUseCase: GetUserUseCase
    val updateUploadedProductsUserUseCase: UpdateUploadedProductsUserUseCase
}