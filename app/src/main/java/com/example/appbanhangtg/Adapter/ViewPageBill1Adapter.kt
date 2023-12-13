package com.example.appbanhangtg.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appbanhangtg.Fragment.PurchaseOrder1.DaGiao
import com.example.appbanhangtg.Fragment.PurchaseOrder1.GiaoHang
import com.example.appbanhangtg.Fragment.PurchaseOrder1.Huy
import com.example.appbanhangtg.Fragment.PurchaseOrder1.LayHang
import com.example.appbanhangtg.Fragment.PurchaseOrder1.XacNhan

class ViewPageBill1Adapter (
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                XacNhan()
            }

            1 -> {
                LayHang()
            }
            2 ->{
                Huy()
            }
            3 ->{
                GiaoHang()
            }
            4 ->{
                DaGiao()
            }

            else -> {
                XacNhan()
            }
        }
    }
}