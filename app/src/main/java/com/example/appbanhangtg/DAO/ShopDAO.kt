package com.example.appbanhangtg.DAO

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.appbanhangtg.Model.ProductModel
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
    fun getByProductIdShop(shopId: Int): List<ShopModel> {
        val shopList = mutableListOf<ShopModel>()

        val allShops = getAllShop()

        for (shop in allShops) {
            if (shop._idShop == shopId) {
                shopList.add(shop)
            }
        }

        return shopList
    }

    fun getProductCountByShopId(shopId: Int): Int {
        val allProducts = getAllShop()
        return allProducts.count { it._idShop == shopId }
    }
    fun addShop(shop: ShopModel): Long {
        val db = sqLiteData.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("nameShop", shop.nameShop)
        contentValues.put("descriptionShop", shop.descriptionShop)
        contentValues.put("sloganShop", shop.sloganShop)
        contentValues.put("imageavtShop", shop.imageavtShop)
        contentValues.put("imageShop", shop.imageShop ?: "")
        contentValues.put("_idUser", shop._idUser)

        val addproduct = db.insert("SHOP", null, contentValues)
        db.close()
        return addproduct
    }
    fun updateShop(shop: ShopModel): Int {
        val db = sqLiteData.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("nameShop", shop.nameShop)
        contentValues.put("descriptionShop", shop.descriptionShop)
        contentValues.put("sloganShop", shop.sloganShop)
        contentValues.put("imageavtShop", shop.imageavtShop)
        contentValues.put("imageShop", shop.imageShop ?: "")
        contentValues.put("_idUser", shop._idUser)

        val updateproduct = db.update("SHOP", contentValues, "_idShop = ?", arrayOf(shop._idShop.toString()))
        db.close()
        return updateproduct
    }
    fun getShopsByUserId(userId: Int): List<ShopModel> {
        val shopsByUser = mutableListOf<ShopModel>()
        val allShops = getAllShop()

        for (shop in allShops) {
            if (shop._idUser == userId) {
                shopsByUser.add(shop)
            }
        }

        return shopsByUser
    }

}