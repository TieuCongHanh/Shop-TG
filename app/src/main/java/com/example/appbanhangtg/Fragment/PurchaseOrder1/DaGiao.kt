package com.example.appbanhangtg.Fragment.PurchaseOrder1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appbanhangtg.DAO.BillDAO
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.FragmentDaGiao2Binding

private lateinit var binding:FragmentDaGiao2Binding
class DaGiao : Fragment() {
    private lateinit var billDAO: BillDAO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDaGiao2Binding.inflate(inflater,container,false)
        // Inflate the layout for this fragment


        return binding.root
    }


}