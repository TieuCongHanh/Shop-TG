package com.example.appbanhangtg.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.appbanhangtg.Adapter.ViewPageVoteProduct1Adapter
import com.example.appbanhangtg.Adapter.ViewPageVoteProductAdapter
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.Model.ProductWrapper
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.Model.ShopWrapper
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityVoteProduct1Binding
import com.google.android.material.tabs.TabLayoutMediator

private lateinit var binding:ActivityVoteProduct1Binding
class VoteProduct1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoteProduct1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val shopModel = intent.getSerializableExtra("PRODUCT_EXTRA") as? ProductModel
        val shopWrapper = ProductWrapper(shopModel)

        binding.imgbackaddProductshop.setOnClickListener { finish() }

        val adapter = ViewPageVoteProduct1Adapter(supportFragmentManager, lifecycle,shopWrapper)
        binding.pagershop.adapter = adapter
        TabLayoutMediator(binding.tablayoutshop, binding.pagershop) { tab, pos ->
            when (pos) {

                0 -> {
                    tab.setCustomView(R.layout.custom_tab_layout_shop)
                    val customView = tab.customView
                    customView?.findViewById<TextView>(R.id.tab_text)?.text = "Đánh giá 1 sao"
                }

                1 -> {
                    tab.setCustomView(R.layout.custom_tab_layout_shop)
                    val customView = tab.customView
                    customView?.findViewById<TextView>(R.id.tab_text)?.text = "Đánh giá 2 sao"
                }
                2 -> {
                    tab.setCustomView(R.layout.custom_tab_layout_shop)
                    val customView = tab.customView
                    customView?.findViewById<TextView>(R.id.tab_text)?.text = "Đánh giá 3 sao"
                }
                3 -> {
                    tab.setCustomView(R.layout.custom_tab_layout_shop)
                    val customView = tab.customView
                    customView?.findViewById<TextView>(R.id.tab_text)?.text = "Đánh giá 4 sao"
                }
                4 -> {
                    tab.setCustomView(R.layout.custom_tab_layout_shop)
                    val customView = tab.customView
                    customView?.findViewById<TextView>(R.id.tab_text)?.text = "Đánh giá 5 sao"
                }


            }
        }.attach()

    }
}