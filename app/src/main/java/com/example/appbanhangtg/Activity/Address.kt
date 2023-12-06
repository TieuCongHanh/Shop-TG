package com.example.appbanhangtg.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appbanhangtg.Adapter.AddressAdapter
import com.example.appbanhangtg.Adapter.ProductAdapter
import com.example.appbanhangtg.DAO.AddRessDAO
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.Model.AddressModel
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityAddressBinding

private lateinit var binding:ActivityAddressBinding
class Address : AppCompatActivity() {
    private lateinit var addRessDAO: AddRessDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = this?.let { SharedPrefsManager.getUser(it) }

        addRessDAO = AddRessDAO(this)
        val userId = user?._idUser

        // Lấy danh sách address theo ID của user
        val addressList = userId?.let {
            addRessDAO.getByAddressIdUser(it)
        }

        addressList?.let { displayAddressList(it) }

        binding.imgbackAddress.setOnClickListener {
            finish()
        }

    }
    private fun displayAddressList(addresss: List<AddressModel>) {
        val recyclerView = binding.recyclerviewAddress

        recyclerView.layoutManager = GridLayoutManager(
            this,
            1,
            GridLayoutManager.VERTICAL,
            false
        )
        val addressAdapter = AddressAdapter(addresss) { clickedProduct ->
            Toast.makeText(this, "${clickedProduct.fullname}.", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = addressAdapter
    }
}