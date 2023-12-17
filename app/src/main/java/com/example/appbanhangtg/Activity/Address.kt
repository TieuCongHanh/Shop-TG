package com.example.appbanhangtg.Activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appbanhangtg.Adapter.AddressAdapter
import com.example.appbanhangtg.Adapter.ProductAdapter
import com.example.appbanhangtg.DAO.AddRessDAO
import com.example.appbanhangtg.Fragment.Product_Shop
import com.example.appbanhangtg.Interface.OnItemLongClickListener
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.Model.AddressModel
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.Model.ShopWrapper
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityAddressBinding

private lateinit var binding: ActivityAddressBinding

class Address : AppCompatActivity() {
    private lateinit var addRessDAO: AddRessDAO

    companion object {
        const val ADD_OR_UPDATE_REQUEST = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode ==ADD_OR_UPDATE_REQUEST) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                listaddress()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addRessDAO = AddRessDAO(this)

        listaddress()

        binding.addAddres.setOnClickListener {
            val intent = Intent(this, AddOrUpdate_Address::class.java)
            intent.putExtra("_idAddress", 0)
            startActivityForResult(intent, ADD_OR_UPDATE_REQUEST)
        }

        binding.imgbackAddress.setOnClickListener {
            finish()
        }

    }

    private fun listaddress() {
        val user = this?.let { SharedPrefsManager.getUser(it) }
        val userId = user?._idUser

        // Lấy danh sách address theo ID của user
        val addressList = userId?.let {
            addRessDAO.getByAddressIdUser(it)
        }

        addressList?.let { displayAddressList(it) }
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
            val selectedAddress = clickedProduct
            val resultIntent = Intent()
            resultIntent.putExtra("EXTRA_ADDRESS", clickedProduct)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()

        }
        addressAdapter.setOnItemLongClickListener(object : OnItemLongClickListener {
            override fun onItemLongClick(address: AddressModel) {
                showOptionsDialog(address)
            }
        })

        recyclerView.adapter = addressAdapter
    }

    private fun showOptionsDialog(address: AddressModel) {
        val options = arrayOf("Xóa", "Cập nhật")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Chọn hành động")
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> deleteAddress(address)
                1 -> updateAddress(address)
            }
        }
        builder.show()
    }

    private fun deleteAddress(address: AddressModel) {
        showDeleteAddressDialog(address)
    }

    private fun updateAddress(address: AddressModel) {
        val intent = Intent(this, AddOrUpdate_Address::class.java)
        intent.putExtra("ADDRESS_EXTRA", address)
        intent.putExtra("_idAddress", address?._idAddRess)
        startActivityForResult(intent, ADD_OR_UPDATE_REQUEST)
    }

    private fun showDeleteAddressDialog(address: AddressModel) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Xóa sản phẩm")
        builder.setMessage("Bạn có chắc muốn xóa sản phẩm này?")
        builder.setPositiveButton("Xóa") { dialog, which -> // Gọi phương thức để xóa bình luận
            addRessDAO.deleteAddress(address._idAddRess)
            Toast.makeText(this, "Xóa địa chỉ thành công", Toast.LENGTH_SHORT).show()
            listaddress()
        }
        builder.setNegativeButton(
            "Hủy"
        ) { dialog, which -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }
}