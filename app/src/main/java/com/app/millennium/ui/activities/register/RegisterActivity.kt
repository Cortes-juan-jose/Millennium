package com.app.millennium.ui.activities.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.millennium.R
import com.app.millennium.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

    }

    private fun initUI(){
        binding.apply {

        }
    }
}