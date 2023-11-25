package com.example.appbanhangtg.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appbanhangtg.Fragment.Home
import com.example.appbanhangtg.Fragment.Profile
import com.example.appbanhangtg.Fragment.QLUser
import com.example.appbanhangtg.Fragment.Shipper

class ViewPageAdminAdapter(
    fragmentManager: FragmentManager, lifecycle : Lifecycle):
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {Home()}
            1 -> {Shipper()}
            2 -> {QLUser()}
            3 -> {Profile()}
            else -> {Home()}
        }
    }
}