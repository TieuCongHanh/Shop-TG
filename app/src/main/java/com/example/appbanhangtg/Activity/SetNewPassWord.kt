package com.example.appbanhangtg.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivitySetNewPassWordBinding

private lateinit var binding:ActivitySetNewPassWordBinding
class SetNewPassWord : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetNewPassWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgbackppasDetail.setOnClickListener {
            finish()
        }
    }
}