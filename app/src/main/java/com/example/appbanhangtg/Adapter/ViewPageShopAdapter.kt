package com.example.appbanhangtg.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appbanhangtg.Fragment.Introduce_Shop
import com.example.appbanhangtg.Fragment.Product_Shop
import com.example.appbanhangtg.Fragment.Vote_Shop
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.Model.ShopWrapper

class ViewPageShopAdapter(
    fragmentManager:FragmentManager,
    lifecycle: Lifecycle,
    private val shopWrapper: ShopWrapper
): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                Introduce_Shop.newInstance(shopWrapper)
            }
            1 ->{
             Vote_Shop.newInstance(shopWrapper)
            }
            2 ->{
                Product_Shop.newInstance(shopWrapper)
            }
            else -> {
                Introduce_Shop.newInstance(shopWrapper)
            }
        }
    }


}