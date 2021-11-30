package com.app.millennium.ui.activities.chat

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.millennium.core.common.Constant
import com.app.millennium.core.common.isNotNull
import com.app.millennium.core.common.toast
import com.app.millennium.data.model.Chat
import com.app.millennium.data.model.Message
import com.app.millennium.data.model.User
import com.app.millennium.databinding.ActivityChatBinding
import com.app.millennium.ui.adapters.message.MessageAdapter
import com.app.millennium.ui.adapters.product_home.ProductHomeAdapter
import com.squareup.picasso.Picasso
import java.util.*

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private val viewModel: ChatViewModel by viewModels()

    private var bundleChat = Bundle()
    private var chat = Chat()

    private var idUserToSession = ""
    private var idChat = ""
    private var userData = User()

    private var messages = mutableListOf<Message>()
    private lateinit var messageAdapter: MessageAdapter

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
        configListMessages() //Configurar la vista de la lista de los mensajes
        configDataChat() //Configrar los datos del chat
        configEventsClick() //Configurar los eventos de la vista
    }

    /**
     * Inicializar los obsevables del viewmodel
     */
    private fun initObservables() {

        //Obtenemos el chat por usuario de la sesion y el usuairo con el que va a chatear
        viewModel.getChatByUserToSessionByUserToChat.observe(
            this,
            { task ->
                task?.let { _task ->
                    _task.addOnFailureListener { exc -> toast("${exc.message}") }

                    _task.addOnSuccessListener { snapshot ->
                        snapshot?.let { querySnapshot ->
                            if (querySnapshot.isEmpty){
                                //Si no existe se crea
                                viewModel.createChat(chat)
                            } else {
                                //Si existe obtenemos el id
                                idChat = querySnapshot.documents[0].id
                                //Y a continuacion obtenemos el id del usuario de la sesion
                                viewModel.getIdUserToSession()
                            }
                        }
                    }
                }
            }
        )

        //Crear chat
        viewModel.createChat.observe(
            this,
            {
                it?.let {
                    it.addOnFailureListener { exc -> toast("${exc.message}") }

                    it.addOnCompleteListener {
                        //Cuando se cree el chat realizamos una consulta para ver si existe
                        //porque la consulta devuelve un valor si el id es idUserToSession+idUserToChat o
                        //viceversa pero el id del chat del mensaje no debe ser ese ya que se crearían
                        //mensajes en distintos chats aunque estos usuarios pertenezcan al mismo chat
                        viewModel.getChatByUserToSessionByUserToChat(chat.idUserToSession!!, chat.idUserToChat!!)
                    }
                }
            }
        )

        //Obtener id del usuario de la sesion
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

        //Obtener usuario por id, se obtendra el usuario con el que va a chatear
        viewModel.getUserById.observe(
            this,
            {
                it?.let { task ->

                    task.addOnFailureListener { exc ->
                        toast("${exc.message}")
                    }

                    task.addOnSuccessListener { document ->
                        document?.let { _document ->
                            if (_document.exists()){
                                //Si el usuario existe lo guardamos
                                userData = _document.toObject(User::class.java)!!
                                //Y seteamos la infoamción del mismo en la vista
                                setDataChat(userData)
                                //Y ahora obtenemos todos los mensajes de un sender de este chat
                                //Para setear el check del visto
                                var idSender = ""
                                //El id sender va a ser el usuario que haya mandado el mensaje
                                if (idUserToSession == chat.idUserToSession){
                                    chat.idUserToChat?.let { id -> idSender = id }
                                } else {
                                    chat.idUserToSession?.let { id -> idSender = id }
                                }
                                viewModel.getAllMessagesByChatBySender(idChat, idSender)
                            }
                        }
                    }
                }
            }
        )

        viewModel.getAllMessagesByChatBySender.observe(
            this,
            {
                it?.let {
                    it.addOnFailureListener { exc -> toast("${exc.message}") }

                    it.addOnSuccessListener { snapshot ->
                        snapshot?.let { _snapshot ->
                            if (!_snapshot.isEmpty){
                                for (document in _snapshot.documents){
                                    //Actualizamos el campo del visto
                                    if (document.exists()){
                                        viewModel.updateMessageViewed(document.id, true)
                                    }
                                }
                            }
                            //Unva vez actualize nos traemos los mensajes
                            viewModel.getAllMessagesByChat(idChat)
                        }
                    }
                }
            }
        )

        //Obtener todos los mensajes por chat
        viewModel.getAllMessagesByChat.observe(
            this,
            {
                it?.let {
                    it.addSnapshotListener { value, error ->
                        //Primero vaciamos la lista si tiene mensajes para que no se dupliquen
                        if (messages.isNotEmpty())
                            messages.clear()

                        //Controlamos que no tenga un error la consulta
                        if (error.isNotNull())
                            return@addSnapshotListener

                        //Ahora obtenemos la lista de mensajes
                        if (value.isNotNull() && !(value?.isEmpty!!)){
                            for (message in value.documents){
                                if (message.exists()){
                                    messages.add(message.toObject(Message::class.java)!!)
                                }
                            }

                            //Una vez tengamos todos los mensajes creamos el adapter
                            //con la lista de los mensjaes
                            messageAdapter = MessageAdapter(messages, idUserToSession)
                            //y le seteamos el adapter al recycler view
                            binding.rvMessages.adapter = messageAdapter
                        }

                    }
                }
            }
        )

        //Crear mensaje
        viewModel.createMessage.observe(
            this,
            {
                it?.let { task ->
                    task.addOnFailureListener { exc -> toast("${exc.message}") }
                    task.addOnCompleteListener { _task ->
                        if (_task.isSuccessful){
                            //Mensaje enviado
                        }
                    }
                }
            }
        )
    }

    /**
     * Metodo que configura la ista de la lista de los mensajes
     */
    private fun configListMessages() {
        val llManager = LinearLayoutManager(this)
        llManager.stackFromEnd = true
        binding.rvMessages.layoutManager = llManager
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

    /**
     * Metodo que configura todos los eventos de la vista
     */
    private fun configEventsClick() {
        binding.ivBack.setOnClickListener {
            finishAndRemoveTask()
        }

        binding.ivSendMessage.setOnClickListener {
            createMessage()
        }
    }

    /**
     * Metodo que crea un mensaje
     */
    private fun createMessage() {

        //Primero obtenemos el texto del campo y verificamos que no sea vacio
        if (binding.etMessage.text.toString().trim().isNotEmpty()){

            //Si no es vacio creamo un msg vacio para ir seteando los valores dependiendo de la configuracion
            val msg = Message()
            /**
             * Se le setea el id del chat obtenido en la consulta para guardar los mensajes que tiene que
             * tiene que ir en un mismo chat
             */
            msg.idChat = idChat
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
            msg.message = binding.etMessage.text.toString().trim()
            msg.timestamp = Date().time
            msg.viewed = false

            binding.etMessage.setText("")

            viewModel.createMessage(msg)
        }

    }
}