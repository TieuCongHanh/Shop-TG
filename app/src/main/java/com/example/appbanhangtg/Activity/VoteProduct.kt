package com.example.appbanhangtg.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.appbanhangtg.Adapter.ViewPageBillAdapter
import com.example.appbanhangtg.Adapter.ViewPageVoteProductAdapter
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityVoteProductBinding
import com.google.android.material.tabs.TabLayoutMediator

private lateinit var binding:ActivityVoteProductBinding
class VoteProduct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoteProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgbackaddProductshop.setOnClickListener { finish() }

        val adapter = ViewPageVoteProductAdapter(supportFragmentManager, lifecycle)
        binding.pagershop.adapter = adapter
        TabLayoutMediator(binding.tablayoutshop, binding.pagershop) { tab, pos ->
            when (pos) {

                0 -> {
                    tab.setCustomView(R.layout.custom_tab_layout_shop)
                    val customView = tab.customView
                    customView?.findViewById<TextView>(R.id.tab_text)?.text = "Đánh giá"
                }

                1 -> {
                    tab.setCustomView(R.layout.custom_tab_layout_shop)
                    val customView = tab.customView
                    customView?.findViewById<TextView>(R.id.tab_text)?.text = "Đã đánh giá"
                }


            }
        }.attach()
    }
}