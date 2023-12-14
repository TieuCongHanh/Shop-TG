package com.example.appbanhangtg.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.appbanhangtg.Adapter.ViewPageBill1Adapter
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityBill1Binding
import com.example.appbanhangtg.databinding.ActivityBill2Binding
import com.google.android.material.tabs.TabLayoutMediator

private lateinit var binding: ActivityBill2Binding
class Bill2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBill2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgbackaddProductshop.setOnClickListener { finish() }

        val adapter = ViewPageBill1Adapter(supportFragmentManager, lifecycle)
        binding.pagershop.adapter = adapter
        TabLayoutMediator(binding.tablayoutshop, binding.pagershop) { tab, pos ->
            when (pos) {

                0 -> {
                    tab.setCustomView(R.layout.custom_tab_layout_shop)
                    val customView = tab.customView
                    customView?.findViewById<TextView>(R.id.tab_text)?.text = "Đơn đang giao"
                }

                1 -> {
                    tab.setCustomView(R.layout.custom_tab_layout_shop)
                    val customView = tab.customView
                    customView?.findViewById<TextView>(R.id.tab_text)?.text = "Đơn đã giao"
                }


            }
        }.attach()

    }
}