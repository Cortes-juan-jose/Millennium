package com.app.millennium.ui.adapters.message

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.millennium.core.common.Constant
import com.app.millennium.core.common.isNotNull
import com.app.millennium.core.common.loadBundle
import com.app.millennium.core.common.openActivity
import com.app.millennium.core.utils.RelativeTime
import com.app.millennium.data.model.Chat
import com.app.millennium.data.model.Message
import com.app.millennium.data.model.User
import com.app.millennium.databinding.ItemListChatBinding
import com.app.millennium.databinding.ItemListMessageChatBinding
import com.app.millennium.domain.use_case.user_auth.GetIdUseCase
import com.app.millennium.domain.use_case.user_db.GetUserUseCase
import com.app.millennium.ui.activities.chat.ChatActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MessageViewHolder(
    private val view : View,
    private val context: Context
) : RecyclerView.ViewHolder(view){

    //Binding
    private val binding: ItemListMessageChatBinding = ItemListMessageChatBinding.bind(view)

    //mensaje
    private var message = Message()
    //id User session
    private var idUserToSession = ""

    //Cargar el chat
    fun loadData(message: Message){
        this.message = message
        initUI()
    }

    //inicializar la vista
    private fun initUI() {
        setDataMessage()
    }

    private fun setDataMessage() {
        binding.mtvMessage.text = message.message
        binding.mtvDatetime.text = RelativeTime.getTimeAgo(message.timestamp, context)
    }

}