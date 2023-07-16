package com.example.userregistration.view


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.userregistration.databinding.ActivityRegisterBinding
import com.example.userregistration.ultils.Constant
import com.example.userregistration.viewmodel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.function.BinaryOperator

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class RegisterActivity : AppCompatActivity(),OnFocusChangeListener {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel:RegisterViewModel by lazy { ViewModelProvider(this).get(RegisterViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        listeners()
        viewModelObserver()
    }

    private fun listeners() {

        binding.firstName.setOnFocusChangeListener(this)
        binding.lastName.setOnFocusChangeListener(this)
        binding.gender.setOnFocusChangeListener(this)
        binding.email.setOnFocusChangeListener(this)
        binding.mobile.setOnFocusChangeListener(this)
        binding.userName.setOnFocusChangeListener(this)
        binding.password.setOnFocusChangeListener(this)

        binding.create.setOnClickListener {
            if (viewModel.finalValidation()){
                viewModel.registerUser(this@RegisterActivity)
            }else{
                Snackbar.make(binding.root, "Please give valid information", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun viewModelObserver(){

        viewModel.validationError.observe(this) {
            when (it.filed){
                Constant.FIRST_NAME -> binding.firstName.error = it.errorMessage
                Constant.LAST_NAME -> binding.lastName.error = it.errorMessage
                Constant.GENDER -> binding.gender.error = it.errorMessage
                Constant.EMAIL -> binding.email.error = it.errorMessage
                Constant.MOBILE -> binding.mobile.error = it.errorMessage
                Constant.USERNAME -> binding.userName.error = it.errorMessage
                Constant.PASSWORD -> binding.password.error = it.errorMessage
            }
        }

        viewModel.userInsertResult.observe(this){
            Toast.makeText(this, "Profile created", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if(!hasFocus){
            view?.let {
                when(it.id){
                    binding.firstName.id -> {viewModel.validateFirstName()}
                    binding.lastName.id -> {viewModel.validateLastName()}
                    binding.gender.id -> {viewModel.validateGender()}
                    binding.email.id -> {viewModel.validateEmail()}
                    binding.mobile.id -> {viewModel.validateMobile()}
                    binding.userName.id -> {viewModel.validateUserName()}
                    binding.password.id -> {viewModel.validatePassword()}
                    else -> {}
                }
            }
        }
    }


}