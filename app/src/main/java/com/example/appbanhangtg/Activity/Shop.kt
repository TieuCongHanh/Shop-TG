package com.example.appbanhangtg.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.appbanhangtg.Adapter.ViewPageShopAdapter
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.Model.ShopWrapper
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityShopBinding
import com.google.android.material.tabs.TabLayoutMediator

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityShopBinding

class Shop : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val shopModel = intent.getSerializableExtra("SHOP_EXTRA") as? ShopModel
        val shopWrapper = ShopWrapper(shopModel)
        val adapter = ViewPageShopAdapter(supportFragmentManager, lifecycle, shopWrapper)

        binding.pagershop.adapter = adapter
        TabLayoutMediator(binding.tablayoutshop, binding.pagershop) { tab, pos ->
            when (pos) {
                0 -> {
                    tab.text = "Giới thiệu"
                }

                1 -> {
                    tab.text = "Đánh giá"
                }

                2 -> {
                    tab.text = "Sản phẩm"
                }
            }
        }.attach()

        shopModel?.let {
            val radiusInPixels =
                binding.root.context.resources.displayMetrics.density * 100 // Chuyển đổi dp sang pixel
            val requestOptions = RequestOptions().transform(CircleCrop())

            Glide.with(binding.root.context)
                .load(it.imageavtShop)
                .apply(requestOptions)
                .placeholder(R.drawable.icon_person) // Placeholder image while loading
                .into(binding.imgavtIntroduce)

            binding.txtnameshopIntroduce.text = it.nameShop + " >"
        }
        binding.imgbackIntroduce.setOnClickListener {
            val user = SharedPrefsManager.getUser(this) // Lấy thông tin người dùng từ SharedPreferences

            val intent: Intent = when (user?.role) {
                "Admin" -> Intent(this, HomeAdmin::class.java)
                "User" -> Intent(this, HomeUser::class.java)
                "Shipper" -> Intent(this, HomeShip::class.java)
                else -> Intent(this, HomeUser::class.java) // Hoặc có thể chuyển đến một màn hình mặc định
            }

            startActivity(intent) // Bắt đầu hoạt động tương ứng với vai trò
        }



    }

}
