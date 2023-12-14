package com.example.appbanhangtg.Fragment.DonMua

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
import com.example.appbanhangtg.Interface.OnDataChangedListener
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.Model.BillModel
import com.example.appbanhangtg.databinding.FragmentDanhGiaBinding

private lateinit var binding: FragmentDanhGiaBinding

class DanhGia : Fragment() , OnDataChangedListener {
    private lateinit var billDAO: BillDAO

    override fun onBillDataChanged() {
        loadDataAndUpdateUI()
    }

    private fun loadDataAndUpdateUI() {
        billDAO = BillDAO(requireContext())
        val user = context?.let { SharedPrefsManager.getUser(it) }

        val userId = user?._idUser
        val billList = userId?.let {
            billDAO.getByBillIdUser(it)
        }
            ?.filter { it.TTXacNhan == "true" && it.TTLayhang == "true" && it.TTHuy == "false" && it.TTDaGiao == "true" && it.TTVote == "false" } // Thay "Trạng thái cần lọc" bằng giá trị cụ thể

        billList?.let { displayBillList(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDanhGiaBinding.inflate(inflater, container, false)

        loadDataAndUpdateUI()


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
        val billAdapter = BillAdapter(requireContext(), bill, billDAO,this) { clickedCart ->
            val productId = clickedCart._idProduct
            Log.d("TTXacNhan", "TT: " + clickedCart.TTXacNhan)
            val productModel = billDAO.getProductByIdProduct(productId)

            val intent = Intent(context, ProductDetail::class.java)
            intent.putExtra("PRODUCT_EXTRA", productModel)
            startActivity(intent)
        }
        recyclerView.adapter = billAdapter
    }
}