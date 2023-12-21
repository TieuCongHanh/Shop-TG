package com.example.appbanhangtg.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.appbanhangtg.Adapter.ViewPageBillAdapter
import com.example.appbanhangtg.Adapter.ViewPageShopAdapter
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityBillBinding
import com.google.android.material.tabs.TabLayoutMediator

private lateinit var binding:ActivityBillBinding
class Bill : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBillBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = this?.let { SharedPrefsManager.getUser(it) }
        binding.imgbackaddProductshop.setOnClickListener {
            val roleuser = user?.role
            if (roleuser == "Admin") {
                val intent = Intent(this, HomeAdmin::class.java)
                intent.putExtra("OPEN_TAB_INDEX", 3) // Ví dụ, mở tab số 0
                startActivity(intent)
            } else if (roleuser == "Shipper") {
                val intent = Intent(this, HomeShip::class.java)
                intent.putExtra("OPEN_TAB_INDEX", 2) // Ví dụ, mở tab số 0
                startActivity(intent)
            } else {
                val intent = Intent(this, HomeUser::class.java)
                intent.putExtra("OPEN_TAB_INDEX", 1) // Ví dụ, mở tab số 0
                startActivity(intent)
            }
        }

        val adapter = ViewPageBillAdapter(supportFragmentManager, lifecycle)
        binding.pagershop.adapter = adapter
        val openTabIndex = intent.getIntExtra("OPEN_TAB_INDEX", -1)
        if (openTabIndex != -1) {
            binding.pagershop.currentItem = openTabIndex
        }
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
            }
        }.attach()

    }
}