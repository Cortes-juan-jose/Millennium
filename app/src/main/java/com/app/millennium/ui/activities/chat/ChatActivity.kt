package com.app.millennium.ui.activities.chat

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.millennium.core.common.Constant
import com.app.millennium.core.common.isNotNull
import com.app.millennium.core.common.toast
import com.app.millennium.data.model.Chat
import com.app.millennium.data.model.Message
import com.app.millennium.data.model.User
import com.app.millennium.databinding.ActivityChatBinding
import com.squareup.picasso.Picasso
import java.util.*

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private val viewModel: ChatViewModel by viewModels()

    private var bundleChat = Bundle()
    private var chat = Chat()

    private var idUserToSession = ""
    private var userData = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Obtenemos el bundle con los datos del chat
        bundleChat = intent.getBundleExtra(Constant.BUNDLE_CHAT)!!

        initUI()
        initObservables()
    }

    private fun initUI(){
        configDataChat()
        configEventsClick()
    }

    private fun initObservables() {

        viewModel.getChatByUserToSessionByUserToChat.observe(
            this,
            { task ->
                task?.let { _task ->
                    _task.addOnFailureListener { exc -> toast("${exc.message}") }

                    _task.addOnSuccessListener { snapshot ->
                        snapshot?.let { querySnapshot ->
                            if (querySnapshot.isEmpty){
                                viewModel.createChat(chat)
                            } else {
                                viewModel.getIdUserToSession()
                            }
                        }
                    }
                }
            }
        )

        viewModel.createChat.observe(
            this,
            {
                it?.let {
                    it.addOnFailureListener { exc -> toast("${exc.message}") }

                    it.addOnCompleteListener {
                        viewModel.getIdUserToSession()
                    }
                }
            }
        )

        viewModel.getIdUserToSession.observe(
            this,
            {
                it?.let { idUserToSession ->
                    this.idUserToSession = idUserToSession
                    //Ahora verificar si el id del usuario de la sesion es igual al id del usuario de la sesion
                    //del chat creado para setear un usuario en la vista u otro
                    if (idUserToSession == chat.idUserToSession){
                        //si es igual entonces obtenemos la información del usuario idUserToChat
                        chat.idUserToChat?.let { id -> viewModel.getUserById(id) }
                    } else {
                        //de lo contrario obtenemos la información del usuario de la sesion iniciada idUserToSession
                        chat.idUserToSession?.let { id -> viewModel.getUserById(id) }
                    }
                }
            }
        )

        viewModel.getUserById.observe(
            this,
            {
                it?.let { task ->

                    task.addOnFailureListener { exc ->
                        toast("${exc.message}")
                    }

                    task.addOnSuccessListener { document ->
                        document?.let { _document ->
                            userData = _document.toObject(User::class.java)!!
                            setDataChat(userData)
                        }
                    }
                }
            }
        )

        viewModel.createMessage.observe(
            this,
            {
                it?.let { task ->
                    task.addOnFailureListener { exc -> toast("${exc.message}") }
                    task.addOnCompleteListener { _task ->
                        if (_task.isSuccessful){
                            toast("Mensaje enviado")
                        }
                    }
                }
            }
        )
    }

    /**
     * Metodo que setea los datos del perfil del chat
     */
    private fun setDataChat(userData: User) {
        if (userData.imgProfile.isNotNull())
            Picasso.get().load(userData.imgProfile).into(binding.civProfileChat)
        binding.mtvNameUser.text = userData.name?.uppercase()
    }

    /**
     * Metodo que crea un chat y lo consulta para ver si existe
     */
    private fun configDataChat() {
        //Obtenemos el chat del bundle
        chat = createChat()

        //Verificamos si existe este chat creado y si es asi que no se guarde
        viewModel.getChatByUserToSessionByUserToChat(chat.idUserToSession!!, chat.idUserToChat!!)
    }

    /**
     * Metodo que crea un chat a partir del bundle del chat obtenido del intent
     */
    private fun createChat(): Chat {
        val chat = Chat()

        chat.id = bundleChat[Constant.PROP_ID_CHAT].toString()
        chat.idUserToSession = bundleChat[Constant.PROP_ID_USER_TO_SESSION_CHAT].toString()
        chat.idUserToChat = bundleChat[Constant.PROP_ID_USER_TO_CHAT_CHAT].toString()
        chat.idsUsers = bundleChat[Constant.PROP_IDS_USERS_CHAT] as ArrayList<String>?
        chat.isWriting = bundleChat[Constant.PROP_IS_WRITING_CHAT].toString().toBoolean()
        chat.timestamp = bundleChat[Constant.PROP_TIMESTAMP_CHAT].toString().toLong()

        return chat
    }

    private fun configEventsClick() {
        binding.ivBack.setOnClickListener {
            finishAndRemoveTask()
        }

        binding.ivSendMessage.setOnClickListener {
            createMessage()
        }
    }

    private fun createMessage() {

        if (binding.etMessage.text.toString().trim().isNotEmpty()){

            val msg = Message()
            msg.idChat = chat.id
            //Ahora verificar si el id del usuario de la sesion es igual al id del usuario de la sesion
            //del chat creado para setear un emisor y un receptor de mensajes
            if (idUserToSession == chat.idUserToSession){
                //si es igual entonces el emisor es el UserToSession
                chat.idUserToSession?.let { id -> msg.idSender = id }
                chat.idUserToChat?.let { id -> msg.idReceiver = id }
            } else {
                //de lo contrario seteamos como emisor el UserToChat
                chat.idUserToSession?.let { id -> msg.idReceiver = id }
                chat.idUserToChat?.let { id -> msg.idSender = id }
            }
            msg.message = binding.etMessage.text.toString()
            msg.timestamp = Date().time
            msg.viewed = false

            binding.etMessage.setText("")

            viewModel.createMessage(msg)
        }

    }
}