package com.example.appbanhangtg.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appbanhangtg.Fragment.PurchaseOrder.DaGiao
import com.example.appbanhangtg.Fragment.PurchaseOrder.DanhGia
import com.example.appbanhangtg.Fragment.PurchaseOrder.GiaoHang
import com.example.appbanhangtg.Fragment.PurchaseOrder.Huy
import com.example.appbanhangtg.Fragment.PurchaseOrder.LayHang
import com.example.appbanhangtg.Fragment.PurchaseOrder.XacNhan


class ViewPageBillAdapter (
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 6
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                XacNhan()
            }

            1 -> {
               LayHang()
            }

            2 -> {
                GiaoHang()
            }
            3 ->{
                Huy()
            }
            4 ->{
                DaGiao()
            }
            5 ->{
                DanhGia()
            }

            else -> {
                DaGiao()
            }
        }
    }
}