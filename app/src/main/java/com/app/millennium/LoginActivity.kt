package com.app.millennium

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.millennium.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    
        initUI()
    }
    
    private fun initUI(){
        
    }
}