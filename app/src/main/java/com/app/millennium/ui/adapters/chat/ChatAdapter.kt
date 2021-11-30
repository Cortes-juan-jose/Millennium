package com.app.millennium.ui.adapters.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.millennium.R
import com.app.millennium.data.model.Chat

class ChatAdapter(
    private val chats : List<Chat>
) : RecyclerView.Adapter<ChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(
            R.layout.item_list_chat,
            parent,
            false
        )
        return ChatViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.loadData(chats[position])
    }

    override fun getItemCount(): Int {
        return chats.size
    }
}