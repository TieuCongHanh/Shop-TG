package com.example.appbanhangtg.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityAccountSettingBinding

private lateinit var binding:ActivityAccountSettingBinding
class AccountSetting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAccountSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtaccountSetting.setOnClickListener {
            val intent = Intent(this,ProfileDetail::class.java)
            startActivity(intent)
        }
        binding.imgbackprofileDetail.setOnClickListener {
            finish()
        }
        binding.txtlogoutSetting.setOnClickListener {
            val user = this?.let { SharedPrefsManager.clearUser(it) }
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }
        binding.txtaccount1Setting.setOnClickListener {
            val intent = Intent(this,SetNewPassWord::class.java)
            startActivity(intent)
        }
        binding.txtaddressSetting.setOnClickListener {
            val intent = Intent(this,Address::class.java)
            startActivity(intent)
        }
    }
}