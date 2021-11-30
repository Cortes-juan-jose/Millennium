package com.app.millennium.ui.adapters.chat

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
import com.app.millennium.databinding.ItemListChatBinding
import com.app.millennium.domain.use_case.user_auth.GetIdUseCase
import com.app.millennium.domain.use_case.user_db.GetUserUseCase
import com.app.millennium.ui.activities.chat.ChatActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ChatViewHolder(
    private val view : View,
    private val context: Context
) : RecyclerView.ViewHolder(view), ChatUsesCases{

    //Binding
    private val binding: ItemListChatBinding = ItemListChatBinding.bind(view)

    //Casos de uso
    override val getUserUseCase: GetUserUseCase
        get() = GetUserUseCase()
    override val getIdUseCase: GetIdUseCase
        get() = GetIdUseCase()

    //chat
    private var chat = Chat()
    //userData
    private var userData = User()
    //id User session
    private var idUserToSession = ""

    //Cargar el chat
    fun loadData(chat: Chat){
        this.chat = chat
        initUI()
    }

    //inicializar la vista
    private fun initUI() {
        getDataUsers()
        configEventsOnClick()
    }

    //Metodo que obtiene los dos usuarios del chat
    private fun getDataUsers() {

        //Primero se obtiene el id del usuario de la sesion iniciada
        CoroutineScope(Dispatchers.IO).launch {
            idUserToSession = getIdUseCase.invoke().toString()

            //Ahora verificar si el id del usuario de la sesion es igual al id del usuario de la sesion
            //del chat creado para setear un usuario en la vista u otro
            if (idUserToSession == chat.idUserToSession){
                //si es igual entonces obtenemos la información del usuario idUserToChat
                configDataUser(chat.idUserToChat)
            } else {
                //de lo contrario obtenemos la información del usuario de la sesion iniciada idUserToSession
                configDataUser(chat.idUserToSession)
            }

        }
    }

    private fun configDataUser(idUser: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            getUserUseCase.invoke(idUser.toString())
                .addOnFailureListener { exc ->
                    Toast.makeText(context, "${exc.message}", Toast.LENGTH_SHORT).show() }

                .addOnSuccessListener {
                    it?.let {
                        userData = it.toObject(User::class.java)!!
                        configDataChat(userData)
                    }
                }
        }
    }

    /**
     * Metodo que setea los datos del chat
     */
    private fun configDataChat(userData: User) {
        if (userData.imgProfile.isNotNull())
            Picasso.get().load(userData.imgProfile).into(binding.civProfileChat)
        binding.mtvNameUser.text = userData.name.toString().uppercase()
        //binding.mtvLastMsg.text = "Ultimo mensaje"
    }

    /**
     * Metodo que registra todos los eventos de la vista del chat
     */
    private fun configEventsOnClick() {

        //Abrir chat
        binding.root.setOnClickListener {

            //Creamos un chat
            chat = Chat(
                id = idUserToSession + userData.id,
                idUserToSession = idUserToSession,
                idUserToChat = userData.id,
                idsUsers = arrayListOf(
                    idUserToSession,
                    userData.id!!
                ),
                isWriting = false,
                timestamp = Date().time
            )
            //Lo guardamos en un bundle
            val bundleChat = chat.loadBundle()
            //Y se lo pasamos al activity
            context.openActivity<ChatActivity> {
                putExtra(Constant.BUNDLE_CHAT, bundleChat)
            }
        }
    }

}