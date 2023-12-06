package com.example.appbanhangtg.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityCarttingBinding

private lateinit var binding:ActivityCarttingBinding
class Cartting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarttingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}