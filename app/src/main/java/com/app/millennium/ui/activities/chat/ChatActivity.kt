package com.app.millennium.ui.activities.chat

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.millennium.core.common.Constant
import com.app.millennium.core.common.toast
import com.app.millennium.data.model.Chat
import com.app.millennium.databinding.ActivityChatBinding
import java.util.ArrayList

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private val viewModel: ChatViewModel by viewModels()

    private lateinit var bundleChat: Bundle
    private lateinit var chat: Chat

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

        //Obtenemos el chat del bundle
        chat = createChat()

        //Verificamos si existe este chat creado y si es asi que no se guarde
        viewModel.getChatByUserToSessionByUserToChat(chat.idUserToSession!!, chat.idUserToChat!!)
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
                        showDataChat()
                    }
                }
            }
        )
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

    private fun showDataChat() {
        toast("Data creada")
    }
}