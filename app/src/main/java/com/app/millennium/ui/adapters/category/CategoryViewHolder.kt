package com.app.millennium.ui.adapters.category

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.millennium.core.common.Constant
import com.app.millennium.core.common.isNotNull
import com.app.millennium.core.common.loadBundle
import com.app.millennium.core.common.openActivity
import com.app.millennium.data.model.Chat
import com.app.millennium.data.model.User
import com.app.millennium.databinding.ItemListCategoryBinding
import com.app.millennium.databinding.ItemListChatBinding
import com.app.millennium.domain.use_case.user_auth.GetIdUseCase
import com.app.millennium.domain.use_case.user_db.GetUserUseCase
import com.app.millennium.ui.activities.chat.ChatActivity
import com.app.millennium.ui.activities.filter_products.FilterProductsActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class CategoryViewHolder(
    private val view : View,
    private val context: Context
) : RecyclerView.ViewHolder(view){

    //Binding
    private val binding: ItemListCategoryBinding = ItemListCategoryBinding.bind(view)

    private var category = ""

    //Cargar el chat
    fun loadData(category: String){
        this.category = category
        initUI()
    }

    //inicializar la vista
    private fun initUI() {

        binding.mtvCategory.text = category

        binding.root.setOnClickListener {
            context.openActivity<FilterProductsActivity> {
                putExtra(Constant.PROP_CATEGORY_PRODUCT, category)
            }
        }
    }

}