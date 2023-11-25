package com.example.appbanhangtg.DAO

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.Model.VoteShopModel
import com.example.appbanhangtg.SQLiteDatabase.SQLiteData

class VoteShopDAO (private val context: Context) {
    private var sqLiteData: SQLiteData = SQLiteData(context)

    @SuppressLint("Range")
    fun getAllVoteShop(): List<VoteShopModel> {
        val voteShopList = mutableListOf<VoteShopModel>()
        val db = sqLiteData.readableDatabase
        val cursor: Cursor? = db.query(
            "VOTESHOP", null, null, null,
            null, null, null, null
        )
        cursor?.use {
            while (it.moveToNext()) {
                val _idVoteShop = it.getInt(it.getColumnIndex("_idVoteShop"))
                val numberofstart = it.getString(it.getColumnIndex("numberofstart"))
                val content = it.getString(it.getColumnIndex("content"))
                val date = it.getString(it.getColumnIndex("date"))
                val _idUser = it.getInt(it.getColumnIndex("_idUser"))
                val _idShop = it.getInt(it.getColumnIndex("_idShop"))
                val product = VoteShopModel(_idVoteShop, numberofstart, content, date,_idUser,_idShop)
                voteShopList.add(product)
            }
        }
        cursor?.close()
        db.close()
        return voteShopList
    }

    fun getByVoteShopId(shopId: Int): List<VoteShopModel> {
        val productList = mutableListOf<VoteShopModel>()

        val allProducts = getAllVoteShop()

        for (product in allProducts) {
            if (product._idShop == shopId) {
                productList.add(product)
            }
        }

        return productList
    }
    fun getVoteShopCountById(shopId: Int): Int {
        val allVoteShops = getAllVoteShop()
        return allVoteShops.count { it._idShop == shopId }
    }
}