package com.example.appbanhangtg.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
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
import com.example.appbanhangtg.databinding.ActivityShopBinding
import com.google.android.material.tabs.TabLayoutMediator

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityShopBinding

class Shop : AppCompatActivity() {

    private lateinit var voteshopDAO: VoteShopDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val shopModel = intent.getSerializableExtra("SHOP_EXTRA") as? ShopModel
        val shopWrapper = ShopWrapper(shopModel)
        val shopId = shopModel?._idShop
        voteshopDAO = VoteShopDAO(this)

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


        shopModel?.let {

            val requestOptions = RequestOptions().transform(CircleCrop())

            Glide.with(binding.root.context)
                .load(it.imageavtShop)
                .apply(requestOptions)
                .placeholder(R.drawable.icon_person) // Placeholder image while loading
                .into(binding.imgavtIntroduce)

            binding.txtnameshopIntroduce.text = it.nameShop + " >"

        }
        binding.imgbackIntroduce.setOnClickListener {
            finish()
        }
        val averageRating = shopId?.let { voteshopDAO.calculateAverageRatingByShopId(it) }
       binding.txtvoteIntroduce.text = "$averageRating"
    }

}
