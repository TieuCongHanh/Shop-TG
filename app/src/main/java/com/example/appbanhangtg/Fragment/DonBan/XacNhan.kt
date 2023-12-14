package com.example.appbanhangtg.Fragment.DonBan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appbanhangtg.Adapter.Bill1Adapter
import com.example.appbanhangtg.DAO.BillDAO
import com.example.appbanhangtg.DAO.ShopDAO
import com.example.appbanhangtg.Interface.OnDataChangedListener
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.Model.BillModel
import com.example.appbanhangtg.databinding.FragmentXacNhan2Binding

private lateinit var binding: FragmentXacNhan2Binding
class XacNhan : Fragment() , OnDataChangedListener {
    private lateinit var billDAO: BillDAO
    private lateinit var shopDAO: ShopDAO
    override fun onBillDataChanged() {
        loadDataAndUpdateUI()
    }

    private fun loadDataAndUpdateUI() {
        billDAO = BillDAO(requireContext())
        shopDAO = ShopDAO(requireContext())

        val currentUser = SharedPrefsManager.getUser(requireContext())
        val userId = currentUser?._idUser

        val billList = billDAO.getAllBill()

        // Lọc danh sách hóa đơn dựa trên idShop và so sánh idUser của shop
        val filteredBillList = billList.filter { bill ->
            val shop = shopDAO.getByProductIdShop(bill._idShop).firstOrNull()
            shop != null && shop._idUser == userId
                    && bill.TTXacNhan == "false" && bill.TTHuy =="false"
        }

        // Hiển thị danh sách hóa đơn đã lọc
        displayBillList(filteredBillList)
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentXacNhan2Binding.inflate(inflater,container,false)

        loadDataAndUpdateUI()
        return binding.root
    }

    private fun displayBillList(bill: List<BillModel>) {
        val recyclerView = binding.recyclerviewBill1

        recyclerView.layoutManager = GridLayoutManager(
            requireContext(),
            1,
            GridLayoutManager.VERTICAL,
            false
        )
        val billAdapter = Bill1Adapter(requireContext(), bill, billDAO,this) { clickedCart ->

        }
        recyclerView.adapter = billAdapter
    }
}