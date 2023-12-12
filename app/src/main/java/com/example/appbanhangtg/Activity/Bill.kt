package com.example.appbanhangtg.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.appbanhangtg.Adapter.ViewPageBillAdapter
import com.example.appbanhangtg.Adapter.ViewPageShopAdapter
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityBillBinding
import com.google.android.material.tabs.TabLayoutMediator

private lateinit var binding:ActivityBillBinding
class Bill : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBillBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ViewPageBillAdapter(supportFragmentManager, lifecycle)
        binding.pagershop.adapter = adapter
        TabLayoutMediator(binding.tablayoutshop, binding.pagershop) { tab, pos ->
            when (pos) {

                0 -> {
                    tab.setCustomView(R.layout.custom_tab_layout_shop)
                    val customView = tab.customView
                    customView?.findViewById<TextView>(R.id.tab_text)?.text = "Chờ xác nhận"
                }

                1 -> {
                    tab.setCustomView(R.layout.custom_tab_layout_shop)
                    val customView = tab.customView
                    customView?.findViewById<TextView>(R.id.tab_text)?.text = "Chờ lấy hàng"
                }

                2 -> {
                    tab.setCustomView(R.layout.custom_tab_layout_shop)
                    val customView = tab.customView
                    customView?.findViewById<TextView>(R.id.tab_text)?.text = "Chờ giao hàng"
                }
                3 -> {
                    tab.setCustomView(R.layout.custom_tab_layout_shop)
                    val customView = tab.customView
                    customView?.findViewById<TextView>(R.id.tab_text)?.text = "Đã Hủy"
                }
                4 -> {
                    tab.setCustomView(R.layout.custom_tab_layout_shop)
                    val customView = tab.customView
                    customView?.findViewById<TextView>(R.id.tab_text)?.text = "Đã giao"
                }
                5 -> {
                    tab.setCustomView(R.layout.custom_tab_layout_shop)
                    val customView = tab.customView
                    customView?.findViewById<TextView>(R.id.tab_text)?.text = "Đánh giá"
                }
            }
        }.attach()

    }
}