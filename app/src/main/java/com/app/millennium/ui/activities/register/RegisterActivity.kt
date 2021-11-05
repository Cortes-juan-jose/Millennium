package com.app.millennium.ui.activities.register

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.millennium.core.common.toast
import com.app.millennium.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel : RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initObservables()
    }

    private fun initUI(){
        viewModel.createAccount("jose@gmail.com", "123456")
        binding.apply {

        }
    }

    private fun initObservables(){
        viewModel.createAccount.observe(
            this,
            { task ->
                task.addOnCompleteListener {
                    if (task.isSuccessful){
                        toast("Registrado")
                    }
                }
                task.addOnFailureListener {
                    toast("${it.message}")
                }
            }
        )
    }
}