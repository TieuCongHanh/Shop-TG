package com.example.appbanhangtg.DAO

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.Model.UserModel
import com.example.appbanhangtg.SQLiteDatabase.SQLiteData

class ShopDAO (context: Context){
    private var sqLiteData: SQLiteData = SQLiteData(context)

    @SuppressLint("Range")
    fun getAllShop(): List<ShopModel> {
        val shopList = mutableListOf<ShopModel>()
        val db = sqLiteData.readableDatabase
        val cursor: Cursor? = db.query(
            "SHOP", null, null, null,
            null, null, null, null
        )
        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndex("_idShop"))
                val nameshop = it.getString(it.getColumnIndex("nameShop"))
                val descriptionShop = it.getString(it.getColumnIndex("descriptionShop"))
                val sloganShop = it.getString(it.getColumnIndex("sloganShop"))
                val imageShop = it.getString(it.getColumnIndex("imageShop"))
                val imageavtShop = it.getString(it.getColumnIndex("imageavtShop"))
                val _idUser = it.getInt(it.getColumnIndex("_idUser"))
                val shop = ShopModel(id, nameshop, descriptionShop, sloganShop, imageShop,imageavtShop, _idUser)
                shopList.add(shop)
            }
        }
        cursor?.close()
        db.close()
        return shopList
    }
}