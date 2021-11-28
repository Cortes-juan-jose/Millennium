package com.app.millennium.ui.activities.profile_user_to_product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.millennium.databinding.ActivityProfileUserToProductBinding

class ProfileUserToProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileUserToProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileUserToProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initObservables()
    }

    private fun initUI(){

    }

    private fun initObservables() {

    }
}