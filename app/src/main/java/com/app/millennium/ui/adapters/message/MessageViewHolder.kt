package com.app.millennium.ui.adapters.message

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.app.millennium.R
import com.app.millennium.core.utils.ConfigThemeApp
import com.app.millennium.core.utils.RelativeTime
import com.app.millennium.data.model.Message
import com.app.millennium.databinding.ItemListMessageChatBinding

class MessageViewHolder(
    private val view : View,
    private val context: Context,
    private val idUserToSession: String
) : RecyclerView.ViewHolder(view){

    //Binding
    private val binding: ItemListMessageChatBinding = ItemListMessageChatBinding.bind(view)

    //mensaje
    private var message = Message()


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

        /**
         * Ahora dependiendo de que si el usuario ha sido el que ha enviado
         * el mensaje se configuraŕa de una manera u otra
         */
        if (idUserToSession == message.idSender)
            configMessageToSender()
        else
            configMessageToReceived()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun configMessageToSender() {
        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        params.addRule(RelativeLayout.ALIGN_PARENT_END)
        params.setMargins(150, 0,0,0)
        binding.llMsg.layoutParams = params
        binding.llMsg.setPadding(30,20,25,20)
        if (ConfigThemeApp.isThemeLight(context))
            binding.llMsg.background = context.getDrawable(R.drawable.custom_message_sended_chat_light)
        else
            binding.llMsg.background = context.getDrawable(R.drawable.custom_message_sended_chat_dark)
        binding.ivCheckMessage.visibility = View.VISIBLE
        binding.mtvDatetime.visibility = View.VISIBLE
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun configMessageToReceived() {

        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        params.addRule(RelativeLayout.ALIGN_PARENT_START)
        params.setMargins(0, 0,150,0)
        binding.llMsg.layoutParams = params
        binding.llMsg.setPadding(30,20,50,20)
        if (ConfigThemeApp.isThemeLight(context))
            binding.llMsg.background = context.getDrawable(R.drawable.custom_message_received_chat_light)
        else
            binding.llMsg.background = context.getDrawable(R.drawable.custom_message_received_chat_dark)
        binding.ivCheckMessage.visibility = View.GONE
        binding.mtvDatetime.visibility = View.VISIBLE
    }

}