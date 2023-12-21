package com.example.appbanhangtg.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appbanhangtg.Adapter.ViewPageAdminAdapter
import com.example.appbanhangtg.databinding.ActivityHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

private lateinit var binding: ActivityHomeBinding

class HomeAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ViewPageAdminAdapter(supportFragmentManager, lifecycle)
        binding.pager.adapter = adapter
        val openTabIndex = intent.getIntExtra("OPEN_TAB_INDEX", -1)
        if (openTabIndex != -1) {
            binding.pager.currentItem = openTabIndex
        }
        TabLayoutMediator(binding.tablayout, binding.pager){tab,pos ->
            when(pos){
                0 -> {tab.text = "Home"}
                1 -> {tab.text = "Shipper"}
                2 -> {tab.text = "QL User"}
                3 -> {tab.text = "Profile"}
            }
        }.attach()

    }
}
