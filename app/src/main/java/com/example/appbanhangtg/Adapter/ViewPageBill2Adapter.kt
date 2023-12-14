package com.example.appbanhangtg.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

import com.example.appbanhangtg.Fragment.DonBan.LayHang
import com.example.appbanhangtg.Fragment.DonBan.XacNhan
import com.example.appbanhangtg.Fragment.DonShip.DaGiao
import com.example.appbanhangtg.Fragment.DonShip.GiaoHang

class ViewPageBill2Adapter  (
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
               GiaoHang()
            }

            1 -> {
                DaGiao()
            }

            else -> {
                GiaoHang()
            }
        }
    }
}