package com.example.appbanhangtg.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appbanhangtg.Adapter.ViewPageAdminAdapter
import com.example.appbanhangtg.Adapter.ViewPageShipAdapter
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityHomeShipBinding
import com.google.android.material.tabs.TabLayoutMediator

private lateinit var binding:ActivityHomeShipBinding
class HomeShip : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeShipBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ViewPageShipAdapter(supportFragmentManager, lifecycle)
        binding.pagership.adapter = adapter
        val openTabIndex = intent.getIntExtra("OPEN_TAB_INDEX", -1)
        if (openTabIndex != -1) {
            binding.pagership.currentItem = openTabIndex
        }
        TabLayoutMediator(binding.tablayoutship, binding.pagership){tab,pos ->
            when(pos){
                0 -> {tab.text = "Home"}
                1 -> {tab.text = "Shipper"}
                2  -> {tab.text = "Profile"}
            }
        }.attach()
    }
}