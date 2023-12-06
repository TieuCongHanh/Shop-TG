package com.example.appbanhangtg.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityAddOrUpdateAddressBinding

private lateinit var binding:ActivityAddOrUpdateAddressBinding
class AddOrUpdate_Address : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddOrUpdateAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}