package com.app.millennium.ui.fragments.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.millennium.core.common.converChat
import com.app.millennium.core.common.isNotNull
import com.app.millennium.core.common.isNull
import com.app.millennium.data.model.Chat
import com.app.millennium.databinding.FragmentChatBinding
import com.app.millennium.ui.adapters.chat.ChatAdapter

class ChatFragment : Fragment() {

    private var _binding : FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChatViewModel by viewModels()

    private lateinit var idUserToSession: String

    private var chats = mutableListOf<Chat>()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initObservables()
    }

    private fun initUI() {
        viewModel.getIdUserToSession()
    }

    private fun initObservables() {

        //Obtener el id del usuario de la sesion
        viewModel.getIdUserToSession.observe(
            viewLifecycleOwner,
            {
                it?.let { idUserToSession ->
                    this.idUserToSession = idUserToSession
                    viewModel.getAllChatsByUser(idUserToSession)
                }
            }
        )

        //Obtener los chats del usuario de la sesion
        viewModel.getAllChatsByUser.observe(
            viewLifecycleOwner,
            {
                it?.let {
                    it.addSnapshotListener { value, error ->

                        if (chats.isNotEmpty())
                            chats.clear()

                        if (error.isNotNull()){
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                            return@addSnapshotListener
                        }

                        if (value.isNull() || value!!.isEmpty){
                            binding.llListChats.visibility = View.GONE
                            binding.progress.visibility = View.GONE
                            binding.clSinChats.visibility = View.VISIBLE
                        } else {

                            for (chat in value.documents){
                                if (chat.isNotNull() && chat.exists()){
                                    chats.add(chat.data.converChat())
                                }
                            }

                            //Una vez tengamos todos los productos creamos el adapter
                            //con la lista de los producots
                            chatAdapter = ChatAdapter(chats)
                            //Configuramos la disposicion del recycler view
                            binding.rvChats.layoutManager = LinearLayoutManager(
                                activity?.applicationContext,
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            //y le seteamos el adapter al recycler view
                            binding.rvChats.adapter = chatAdapter

                            //Escondemos el texto sin productos el progress y mostramos la lista
                            binding.llListChats.visibility = View.VISIBLE
                            binding.clSinChats.visibility = View.GONE
                            binding.progress.visibility = View.GONE
                        }
                    }
                }
            }
        )
    }
}