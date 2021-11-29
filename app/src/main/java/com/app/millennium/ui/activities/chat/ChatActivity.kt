package com.app.millennium.ui.activities.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.millennium.core.common.Constant
import com.app.millennium.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding

    private lateinit var bundleChat: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bundleChat = intent.getBundleExtra(Constant.BUNDLE_CHAT)!!

        initUI()
        initObservables()
    }

    private fun initUI(){

    }

    private fun initObservables() {

    }
}