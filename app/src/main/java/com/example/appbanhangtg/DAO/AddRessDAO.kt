package com.example.appbanhangtg.DAO

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import com.example.appbanhangtg.Model.AddressModel
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.SQLiteDatabase.SQLiteData

class AddRessDAO(private val context: Context) {
    private var sqLiteData: SQLiteData = SQLiteData(context)
    @SuppressLint("Range")
    fun getAllAddress(): List<AddressModel> {
        val addressList = mutableListOf<AddressModel>()
        val db = sqLiteData.readableDatabase
        val cursor: Cursor? = db.query(
            "ADDRESS", null, null, null,
            null, null, null, null
        )
        cursor?.use {
            while (it.moveToNext()) {
                val _idAddRess = it.getInt(it.getColumnIndex("_idAddRess"))
                val fullname = it.getString(it.getColumnIndex("fullname"))
                val phone = it.getString(it.getColumnIndex("phone"))
                val address = it.getString(it.getColumnIndex("address"))
                val note = it.getString(it.getColumnIndex("note"))
                val _idUser = it.getInt(it.getColumnIndex("_idUser"))
                val addresslist = AddressModel(_idAddRess, fullname, phone, address, note, _idUser)
                addressList.add(addresslist)
            }
        }
        cursor?.close()
        db.close()
        return addressList
    }
    fun getByAddressIdUser(userId: Int): List<AddressModel> {
        val addressList = mutableListOf<AddressModel>()

        val allAddress = getAllAddress()

        for (user in allAddress) {
            if (user._idUser == userId) {
                addressList.add(user)
            }
        }

        return addressList
    }
}