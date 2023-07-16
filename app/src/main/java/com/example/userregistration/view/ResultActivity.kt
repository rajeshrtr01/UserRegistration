package com.example.userregistration.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.userregistration.R
import com.example.userregistration.databinding.ActivityResultBinding
import com.example.userregistration.db.entity.UserEntity

class ResultActivity : AppCompatActivity() {
    private lateinit var binding:ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val user = intent.getParcelableExtra<UserEntity>("USER")

        binding.user = user


    }
}