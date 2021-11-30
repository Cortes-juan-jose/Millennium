package com.app.millennium.ui.adapters.message

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.millennium.R
import com.app.millennium.data.model.Message

class MessageAdapter(
    private val messages : List<Message>
) : RecyclerView.Adapter<MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(
            R.layout.item_list_message_chat,
            parent,
            false
        )
        return MessageViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.loadData(messages[position])
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}