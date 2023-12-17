package com.example.appbanhangtg.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appbanhangtg.Fragment.StarProduct.StarProduct
import com.example.appbanhangtg.Fragment.StarProduct.StarProduct1
import com.example.appbanhangtg.Fragment.StarProduct.StarProduct2
import com.example.appbanhangtg.Fragment.StarProduct.StarProduct3
import com.example.appbanhangtg.Fragment.StarProduct.StarProduct4
import com.example.appbanhangtg.Model.ProductWrapper
import com.example.appbanhangtg.Model.ShopWrapper


class ViewPageVoteProduct1Adapter (
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val productWrapper: ProductWrapper
): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                StarProduct.newInstance(productWrapper)
            }

            1 -> {
                StarProduct1.newInstance(productWrapper)
            }
            2 -> {
                StarProduct2.newInstance(productWrapper)
            }
            3 -> {
                StarProduct3.newInstance(productWrapper)
            }
            4 ->{
                StarProduct4.newInstance(productWrapper)
            }

            else -> {
                StarProduct4.newInstance(productWrapper)
            }
        }
    }
}