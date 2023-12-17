package com.example.appbanhangtg.DAO

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.appbanhangtg.Model.AddressModel
import com.example.appbanhangtg.Model.BillModel
import com.example.appbanhangtg.Model.ProductModel
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
    @SuppressLint("Range")
    fun getAddressById(AddressId: Int): AddressModel? {
        val db = sqLiteData.readableDatabase
        var address1: AddressModel? = null

        val selectQuery = "SELECT * FROM ADDRESS WHERE _idAddRess = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(AddressId.toString()))

        if (cursor.moveToFirst()) {
            val _idAddRess = cursor.getInt(cursor.getColumnIndex("_idAddRess"))
            val fullname = cursor.getString(cursor.getColumnIndex("fullname"))
            val phone = cursor.getString(cursor.getColumnIndex("phone"))
            val address = cursor.getString(cursor.getColumnIndex("address"))
            val note = cursor.getString(cursor.getColumnIndex("note"))
            val _idUser = cursor.getInt(cursor.getColumnIndex("_idUser"))

            address1 = AddressModel(_idAddRess, fullname, phone, address, note, _idUser)

        }

        cursor.close()
        db.close()
        return address1
    }
    fun addAddress(address: AddressModel): Long {
        val db = sqLiteData.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("fullname", address.fullname)
        contentValues.put("phone", address.phone)
        contentValues.put("address", address.address)
        contentValues.put("note", address.note)
        contentValues.put("_idUser", address._idUser)

        val addAddress = db.insert("ADDRESS", null, contentValues)
        db.close()
        return addAddress
    }
    fun updateAddress(address: AddressModel): Int {
        val db = sqLiteData.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("fullname", address.fullname)
        contentValues.put("phone", address.phone)
        contentValues.put("address", address.address)
        contentValues.put("note", address.note)
        contentValues.put("_idUser", address._idUser)

        val updateproduct = db.update("ADDRESS", contentValues, "_idAddRess = ?", arrayOf(address._idAddRess.toString()))
        db.close()
        return updateproduct
    }
    fun deleteAddress(addressId: Int): Int {
        val db = sqLiteData.writableDatabase
        val deleteAddress = db.delete("ADDRESS", "_idAddRess = ?", arrayOf(addressId.toString()))
        db.close()
        return deleteAddress
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