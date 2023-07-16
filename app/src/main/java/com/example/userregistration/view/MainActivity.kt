package com.example.userregistration.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.userregistration.databinding.ActivityMainBinding
import com.example.userregistration.ultils.Constant
import com.example.userregistration.viewmodel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(),OnFocusChangeListener {

    lateinit var binding: ActivityMainBinding
    private val viewModel: RegisterViewModel by lazy { ViewModelProvider(this).get(RegisterViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.register.setOnClickListener {
            startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
        }

        binding.userName.setOnFocusChangeListener(this)
        binding.password.setOnFocusChangeListener(this)

        binding.login.setOnClickListener {
            if(viewModel.validateUserName() && viewModel.validatePassword()){
                viewModel.loginUser(this@MainActivity)
            }else{
                Snackbar.make(binding.root, "Please provide valid information", Snackbar.LENGTH_SHORT)
            }
        }

        viewModelObserver()
    }

    private fun viewModelObserver(){

        viewModel.validationError.observe(this) {
            when (it.filed){
                Constant.USERNAME -> binding.userName.error = it.errorMessage
                Constant.PASSWORD -> binding.password.error = it.errorMessage
            }
        }

        viewModel.loginResult.observe(this){
            if (it == null){
                Toast.makeText(this@MainActivity, "Invalid username/password", Toast.LENGTH_SHORT).show()
            }else{
                val userIntent = Intent(this@MainActivity, ResultActivity::class.java)
                userIntent.putExtra("USER", it)
                startActivity(userIntent)
            }
        }

    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if(!hasFocus){
            view?.let {
                when(it.id){
                    binding.userName.id -> {viewModel.validateUserName()}
                    binding.password.id -> {viewModel.validatePassword()}
                    else -> {}
                }
            }
        }
    }
}