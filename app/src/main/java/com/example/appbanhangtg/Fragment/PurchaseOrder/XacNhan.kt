package com.example.appbanhangtg.Fragment.PurchaseOrder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appbanhangtg.Activity.ProductDetail

import com.example.appbanhangtg.Adapter.BillAdapter
import com.example.appbanhangtg.DAO.BillDAO
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.Model.BillModel
import com.example.appbanhangtg.databinding.FragmentXacNhanBinding

private lateinit var binding:FragmentXacNhanBinding
class XacNhan : Fragment() {
    private lateinit var billDAO: BillDAO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentXacNhanBinding.inflate(inflater,container,false)
        billDAO = BillDAO(requireContext())
        val user = context?.let { SharedPrefsManager.getUser(it) }

        val userId = user?._idUser
        val billList = userId?.let {
            billDAO.getByBillIdUser(it)
        }?.filter { it.TTXacNhan == "false" } // Thay "Trạng thái cần lọc" bằng giá trị cụ thể

        billList?.let { displayBillList(it) }



        return binding.root
    }
    private fun displayBillList(bill: List<BillModel>) {
        val recyclerView = binding.recyclerviewBill

        recyclerView.layoutManager = GridLayoutManager(
            requireContext(),
            1,
            GridLayoutManager.VERTICAL,
            false
        )
        val billAdapter = BillAdapter(requireContext(), bill, billDAO) { clickedCart ->
            val productId = clickedCart._idProduct
            Log.d("TTXacNhan","TT: " + clickedCart.TTXacNhan)
            val productModel = billDAO.getProductByIdProduct(productId)

            val intent = Intent(context, ProductDetail::class.java)
            intent.putExtra("PRODUCT_EXTRA", productModel)
            startActivity(intent)
        }
        recyclerView.adapter = billAdapter
    }
}