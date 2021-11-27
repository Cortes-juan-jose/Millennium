package com.app.millennium.ui.adapters.product_home

import com.app.millennium.domain.use_case.likes_db.SaveLikeUseCase

interface ProductHomeUsesCases {
    val saveLikeUseCase: SaveLikeUseCase
}