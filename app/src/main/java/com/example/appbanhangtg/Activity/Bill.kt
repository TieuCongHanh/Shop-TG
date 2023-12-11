package com.example.appbanhangtg.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityBillBinding

private lateinit var binding:ActivityBillBinding
class Bill : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBillBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}