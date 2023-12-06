package com.example.appbanhangtg.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityProfileDetailBinding

private lateinit var binding:ActivityProfileDetailBinding
class ProfileDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgbackprofileDetail.setOnClickListener {
            finish()
        }
        val user = this?.let { SharedPrefsManager.getUser(it) }
        val requestOptions = RequestOptions().transform(CircleCrop())

        Glide.with(binding.root.context)
            .load(user?.image)
            .apply(requestOptions)
            .placeholder(R.drawable.icon_person) // Placeholder image while loading
            .into(binding.imgprofileDetail)

       binding.edtusernameprofileDetail.setText(" " +user?.username)
       binding.edtemailprofileDetail.setText(" " +user?.email)
       binding.edtphoneprofileDetail.setText(" " +user?.phone)
    }
}