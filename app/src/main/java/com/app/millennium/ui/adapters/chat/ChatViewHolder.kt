package com.app.millennium.ui.adapters.chat

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.millennium.R
import com.app.millennium.core.common.isNotNull
import com.app.millennium.data.model.Chat
import com.app.millennium.data.model.User
import com.app.millennium.databinding.ItemListChatBinding
import com.app.millennium.domain.use_case.user_db.GetUserUseCase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatViewHolder(
    private val view : View,
    private val context: Context
) : RecyclerView.ViewHolder(view), ChatUsesCases{

    //Binding
    private val binding: ItemListChatBinding = ItemListChatBinding.bind(view)

    //Casos de uso
    override val getUserUseCase: GetUserUseCase
        get() = GetUserUseCase()

    //chat
    private lateinit var chat: Chat
    //userToSession
    private var userToSession = User()
    //userToChat
    private var userToChat = User()

    //Cargar el chat
    fun loadData(chat: Chat){
        this.chat = chat
        initUI()
    }

    //inicializar la vista
    private fun initUI() {
        getDataUsers()
        configDataChat()
        configEventsOnClick()
    }

    //Metodo que obtiene los dos usuarios del chat
    private fun getDataUsers() {

        //Obtenemos el usuario de la sesion y cuando se obtenga, obtenemos el usuario del chat
        CoroutineScope(Dispatchers.IO).launch {
            chat.idUserToSession?.let { idUserToSession ->
                getUserUseCase.invoke(idUserToSession)
                    .addOnFailureListener { exc -> Toast.makeText(context, "${exc.message}", Toast.LENGTH_SHORT).show() }
                    .addOnSuccessListener { document ->
                        document?.let { _document ->
                            if (_document.isNotNull() && _document.exists()){
                                userToSession = _document.toObject(User::class.java)!!
                                Toast.makeText(context, userToSession.toString(), Toast.LENGTH_SHORT).show()
                                //Obtenemos el usuario del chat
                                CoroutineScope(Dispatchers.IO).launch {
                                    chat.idUserToChat?.let { iduserToChat ->
                                        getUserUseCase.invoke(iduserToChat)
                                            .addOnFailureListener { exc -> Toast.makeText(context, "${exc.message}", Toast.LENGTH_SHORT).show() }
                                            .addOnSuccessListener { document ->
                                                document?.let { _document ->
                                                    if (_document.isNotNull() && _document.exists()){
                                                        userToChat = _document.toObject(User::class.java)!!
                                                        Toast.makeText(context, userToChat.toString(), Toast.LENGTH_SHORT).show()
                                                        //Ya se han obtenido los dos usuarios ahora seteamos la vista
                                                        configDataChat()
                                                    }
                                                }
                                            }
                                    }
                                }
                            }
                        }
                    }
            }
        }
    }

    /**
     * Metodo que setea los datos del chat
     */
    private fun configDataChat() {
        if (userToChat.imgProfile.isNotNull())
            Picasso.get().load(userToChat.imgProfile).into(binding.civProfileChat)
        binding.mtvNameUser.text = userToChat.name
        binding.mtvLastMsg.text = ""
    }

    /**
     * Metodo que registra todos los eventos de la vista del chat
     */
    private fun configEventsOnClick() {

        //Abrir chat
        binding.root.setOnClickListener {
            Toast.makeText(context, "Abrir chat", Toast.LENGTH_SHORT).show()
        }
    }

}