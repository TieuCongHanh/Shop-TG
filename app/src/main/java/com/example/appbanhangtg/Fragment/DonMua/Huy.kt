package com.example.appbanhangtg.Fragment.DonMua

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appbanhangtg.Activity.BillDetail
import com.example.appbanhangtg.Activity.ProductDetail
import com.example.appbanhangtg.Adapter.BillAdapter
import com.example.appbanhangtg.DAO.BillDAO
import com.example.appbanhangtg.Interface.OnDataChangedListener
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.Model.BillModel
import com.example.appbanhangtg.databinding.FragmentHuyBinding

private lateinit var binding:FragmentHuyBinding
class Huy : Fragment()  , OnDataChangedListener {
    private lateinit var billDAO: BillDAO

    override fun onBillDataChanged() {
        loadDataAndUpdateUI()
    }
    override fun onResume() {
        super.onResume()
        loadDataAndUpdateUI()
    }
    private fun loadDataAndUpdateUI() {
        billDAO = BillDAO(requireContext())
        val user = context?.let { SharedPrefsManager.getUser(it) }

        val userId = user?._idUser
        val billList = userId?.let {
            billDAO.getByBillIdUser(it)
        }?.filter {  it.TTHuy == "true"  } // Thay "Trạng thái cần lọc" bằng giá trị cụ thể

        billList?.let { displayBillList(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHuyBinding.inflate(inflater,container,false)
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
            val intent = Intent(requireContext(), BillDetail::class.java)
            intent.putExtra("BILL_EXTRA", clickedCart)
            startActivity(intent)
        }
        recyclerView.adapter = billAdapter
    }

}