package com.example.appbanhangtg.Activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.appbanhangtg.DAO.AddRessDAO
import com.example.appbanhangtg.DAO.ProductDAO
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.Model.AddressModel
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.Model.ShopWrapper
import com.example.appbanhangtg.Model.TypeProductModel
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityAddOrUpdateAddressBinding

private lateinit var binding: ActivityAddOrUpdateAddressBinding

class AddOrUpdate_Address : AppCompatActivity() {

    private val addRessDAO: AddRessDAO by lazy { AddRessDAO(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddOrUpdateAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addressModel = intent.getSerializableExtra("ADDRESS_EXTRA") as? AddressModel
        val user = this?.let { SharedPrefsManager.getUser(it) }
        val addressId = intent.getIntExtra("_idAddress", 0)
        if (addressId == 0) {
            binding.txtaddAddress.setText("Thêm địa chỉ")
            binding.btnaddAddress.setText("Add Address")
            binding.btnaddAddress.setOnClickListener {

                val fullname = binding.edtFullname.text.toString()
                val phone = binding.edtPhone.text.toString()
                val address = binding.edtAddress.text.toString()
                val note = binding.edtNote.text.toString()
                val iduser = user?._idUser

                val phoneNumberRegex = Regex("^(03|05|07|08|09)\\d{8}$")
                val isValid = phoneNumberRegex.matches(phone)

                if (fullname.isEmpty() || phone.isEmpty() || address.isEmpty() || note.isEmpty()) {
                    Toast.makeText(this, "Bạn cần nhập thông tin", Toast.LENGTH_SHORT).show()
                }else if (fullname.length < 10){
                    Toast.makeText(this, "Họ và tên phải lớn hơn 9 kí tự", Toast.LENGTH_SHORT).show()
                }else if (address.length < 16 ){
                    Toast.makeText(this, "Địa chỉ phải lớn hơn 15 kí tự", Toast.LENGTH_SHORT).show()
                }else if (!isValid){
                    Toast.makeText(this, "Sai định dạng số điện thoại Việt Nam", Toast.LENGTH_SHORT).show()
                }
                else {
                    if (iduser != null) {
                        hamAdd(
                            fullname,
                            phone,
                            address,
                            note,
                            iduser
                        )
                    }
                }
            }
        } else {
            binding.txtaddAddress.setText("cập nhập địa chỉ")
            binding.btnaddAddress.setText("Update Address")
            val currentAddress =
                addRessDAO.getAllAddress().find { it._idAddRess == addressId }
            if (currentAddress != null) {
                displayAddressInfo(currentAddress)
                binding.btnaddAddress.setOnClickListener {

                    val fullname = binding.edtFullname.text.toString()
                    val phone = binding.edtPhone.text.toString()
                    val address = binding.edtAddress.text.toString()
                    val note = binding.edtNote.text.toString()
                    val iduser = user?._idUser

                    val phoneNumberRegex = Regex("^(03|05|07|08|09)\\d{8}$")
                    val isValid = phoneNumberRegex.matches(phone)

                    if (fullname.isEmpty() || phone.isEmpty() || address.isEmpty() || note.isEmpty()) {
                        Toast.makeText(this, "Bạn cần nhập thông tin", Toast.LENGTH_SHORT).show()
                    }else if (fullname.length <= 10){
                        Toast.makeText(this, "Họ và tên phải lớn hơn 10 kí tự", Toast.LENGTH_SHORT).show()
                    }else if (address.length <= 20 ){
                        Toast.makeText(this, "Địa chỉ phải lớn hơn 20 kí tự", Toast.LENGTH_SHORT).show()
                    }else if (!isValid){
                        Toast.makeText(this, "Sai định dạng số điện thoại Việt Nam", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        if (iduser != null) {
                            hamUpdate(
                                addressId,
                                fullname,
                                phone,
                                address,
                                note,
                                iduser
                            )
                        }
                    }
                }
            }
        }

        binding.imgbackaddAddress.setOnClickListener {
            finish()
        }
    }
    private fun hamAdd(
        fullname: String,
        phone: String,
        address: String,
        note: String,
        iduser: Int

    ) {
        val newaddress = AddressModel(
            0,
            fullname,
            phone,
            address,
            note,
            iduser
        )
        val typeId = addRessDAO.addAddress(newaddress)

        if (typeId > -1) {
            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show()
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        } else {
            Toast.makeText(this, "thêm thất bại", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hamUpdate(
        addressId: Int,
        fullname: String,
        phone: String,
        address: String,
        note: String,
        iduser: Int
    ) {
        val updateAddress = AddressModel(
            addressId,
            fullname,
            phone,
            address,
            note,
            iduser
        )
        val rowsAffected = addRessDAO.updateAddress(updateAddress)

        if (rowsAffected > 0) {
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        } else {
            Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayAddressInfo(addressModel: AddressModel) {
        binding.edtFullname.setText(addressModel.fullname)
        binding.edtPhone.setText(addressModel.phone)
        binding.edtAddress.setText(addressModel.address)
        binding.edtNote.setText(addressModel.note)

    }

}

