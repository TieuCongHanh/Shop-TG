package com.example.appbanhangtg.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appbanhangtg.Adapter.ViewPageAdminAdapter
import com.example.appbanhangtg.Adapter.ViewPageUserAdapter
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityHomeUserBinding
import com.google.android.material.tabs.TabLayoutMediator

private lateinit var binding: ActivityHomeUserBinding
class HomeUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = this?.let {
            SharedPrefsManager.getUser(it)
        }
        val roleuser = user?.role
        if (roleuser == "Admin"){
            val intent = Intent(this,HomeAdmin::class.java)
            startActivity(intent)
        }else if (roleuser == "Shipper"){
            val intent = Intent(this,HomeShip::class.java)
            startActivity(intent)
        }

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