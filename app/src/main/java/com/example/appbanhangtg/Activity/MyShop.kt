package com.example.appbanhangtg.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.appbanhangtg.Adapter.ViewPageShopAdapter
import com.example.appbanhangtg.Adapter.VoteShopAdapter
import com.example.appbanhangtg.DAO.ShopDAO
import com.example.appbanhangtg.DAO.VoteShopDAO
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.Model.ShopWrapper
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityMyShopBinding
import com.example.appbanhangtg.databinding.ActivityShopBinding
import com.google.android.material.tabs.TabLayoutMediator

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityMyShopBinding

class MyShop : AppCompatActivity() {

    private lateinit var voteshopDAO: VoteShopDAO
    private lateinit var shopDAO: ShopDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyShopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var shopModel = intent.getSerializableExtra("SHOP_EXTRA") as? ShopModel

        val shopId = shopModel?._idShop
        val user = this?.let { SharedPrefsManager.getUser(this) }
        val userId = user?._idUser


        shopDAO = ShopDAO(this)
        voteshopDAO = VoteShopDAO(this)

            val shopsByUserId = shopDAO.getShopsByUserId(userId ?: -1) // Lấy danh sách cửa hàng theo userId

            if (shopsByUserId.isNotEmpty()) {
                // Nếu có cửa hàng với userId này
                val currentShop = shopsByUserId[0] // Lấy cửa hàng đầu tiên trong danh sách
                binding.txtfolloworaddshop.visibility = View.GONE

                shopModel = ShopModel(
                    currentShop._idShop,
                    currentShop.nameShop,
                    currentShop.descriptionShop,
                    currentShop.sloganShop,
                    currentShop.imageavtShop,
                    currentShop.imageShop,
                    currentShop._idUser
                )


                val requestOptions = RequestOptions().transform(CircleCrop())

                Glide.with(binding.root.context)
                    .load(currentShop.imageavtShop)
                    .apply(requestOptions)
                    .placeholder(R.drawable.icon_person)
                    .into(binding.imgavtIntroduce)

                binding.txtnameshopIntroduce.text = currentShop.nameShop + " >"

                val averageRating = currentShop._idShop?.let { voteshopDAO.calculateAverageRatingByShopId(it) }
                binding.txtvoteIntroduce.text = "$averageRating"
            } else {
                // Nếu không có cửa hàng với userId này
                binding.txtfolloworaddshop.setText("Tạo shop")
                binding.txtfolloworaddshop.setOnClickListener {
                    Toast.makeText(this, "Đã thêm shop", Toast.LENGTH_SHORT).show()
                }
            }

        binding.imgbackIntroduce.setOnClickListener {
            finish()
        }

        val shopWrapper = ShopWrapper(shopModel)
        val adapter = ViewPageShopAdapter(supportFragmentManager, lifecycle, shopWrapper)
        binding.pagershop.adapter = adapter
        TabLayoutMediator(binding.tablayoutshop, binding.pagershop) { tab, pos ->
            when (pos) {

                0 -> {
                    tab.setCustomView(R.layout.custom_tab_layout_shop)
                    val customView = tab.customView
                    customView?.findViewById<ImageView>(R.id.tab_icon)?.setImageResource(R.drawable.icon_bolt)
                    customView?.findViewById<TextView>(R.id.tab_text)?.text = "Giới thiệu"
                }
                1 -> {
                    tab.setCustomView(R.layout.custom_tab_layout_shop)
                    val customView = tab.customView
                    customView?.findViewById<ImageView>(R.id.tab_icon)?.setImageResource(R.drawable.ngoisao)
                    customView?.findViewById<TextView>(R.id.tab_text)?.text = "Đánh giá"
                }
                2 -> {
                    tab.setCustomView(R.layout.custom_tab_layout_shop)
                    val customView = tab.customView
                    customView?.findViewById<ImageView>(R.id.tab_icon)?.setImageResource(R.drawable.icon_bill)
                    customView?.findViewById<TextView>(R.id.tab_text)?.text = "Sản phẩm"
                }
            }
        }.attach()


    }
}
