package com.example.appbanhangtg.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appbanhangtg.Adapter.ViewPageAdminAdapter
import com.example.appbanhangtg.Adapter.ViewPageUserAdapter
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityHomeUserBinding
import com.google.android.material.tabs.TabLayoutMediator

private lateinit var binding: ActivityHomeUserBinding
class HomeUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ViewPageUserAdapter(supportFragmentManager, lifecycle)
        binding.pageruser.adapter = adapter
        TabLayoutMediator(binding.tablayoutuser, binding.pageruser){tab,pos ->
            when(pos){
                0 -> {tab.text = "Home"}
                1  -> {tab.text = "Profile"}
            }
        }.attach()
    }
}