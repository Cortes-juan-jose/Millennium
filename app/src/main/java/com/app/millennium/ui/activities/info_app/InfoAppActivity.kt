package com.app.millennium.ui.activities.info_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.millennium.R
import com.app.millennium.databinding.ActivityInfoAppBinding

class InfoAppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInfoAppBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
    
        initUI()
    }
    
    private fun initUI(){

    }
}