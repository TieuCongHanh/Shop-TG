package com.example.appbanhangtg.DAO

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.appbanhangtg.Model.VoteProductModel
import com.example.appbanhangtg.Model.VoteShopModel
import com.example.appbanhangtg.SQLiteDatabase.SQLiteData

class VoteProductDAO (private val context: Context) {
    private var sqLiteData: SQLiteData = SQLiteData(context)

    @SuppressLint("Range")
    fun getAllVoteProduct(): List<VoteProductModel> {
        val voteShopList = mutableListOf<VoteProductModel>()
        val db = sqLiteData.readableDatabase
        val cursor: Cursor? = db.query(
            "VOTEPRODUCT", null, null, null,
            null, null, null, null
        )
        cursor?.use {
            while (it.moveToNext()) {
                val _idVoteProduct = it.getInt(it.getColumnIndex("_idVoteProduct"))
                val numberofstart = it.getString(it.getColumnIndex("numberofstart"))
                val content = it.getString(it.getColumnIndex("content"))
                val date = it.getString(it.getColumnIndex("date"))
                val _idUser = it.getInt(it.getColumnIndex("_idUser"))
                val _idProduct = it.getInt(it.getColumnIndex("_idProduct"))
                val product =
                    VoteProductModel(_idVoteProduct, numberofstart, content, date, _idUser, _idProduct)
                voteShopList.add(product)
            }
        }
        cursor?.close()
        db.close()
        return voteShopList
    }

    fun getByVoteProductIdabc(productId: Int): List<VoteProductModel> {
        val VoteList = mutableListOf<VoteProductModel>()

        val allvote = getAllVoteProduct()

        for (vote in allvote) {
            if (vote._idProduct == productId) {
                VoteList.add(vote)
            }
        }

        return VoteList
    }
    fun getByVoteShopId(productId: Int): List<VoteProductModel> {
        val VoteList = mutableListOf<VoteProductModel>()

        val allvote = getAllVoteProduct()

        for (vote in allvote) {
            if (vote._idProduct == productId && vote.numberofstart == "1") {
                VoteList.add(vote)
            }
        }

        return VoteList
    }

    fun getStarCoun1tById(productId: Int): Int {
        val allVoteShops = getAllVoteProduct()
        return allVoteShops.count { it._idProduct == productId && it.numberofstart == "2" }
    }

    fun getByVote1ShopId(productId: Int): List<VoteProductModel> {
        val VoteList = mutableListOf<VoteProductModel>()

        val allvote = getAllVoteProduct()

        for (vote in allvote) {
            if (vote._idProduct == productId && vote.numberofstart == "2") {
                VoteList.add(vote)
            }
        }

        return VoteList
    }

    fun getStarCoun2tById(productId: Int): Int {
        val allVote = getAllVoteProduct()
        return allVote.count { it._idProduct == productId && it.numberofstart == "3" }
    }

    fun getByVote2ShopId(productId: Int): List<VoteProductModel> {
        val VoteList = mutableListOf<VoteProductModel>()

        val allvote = getAllVoteProduct()

        for (vote in allvote) {
            if (vote._idProduct == productId && vote.numberofstart == "3") {
                VoteList.add(vote)
            }
        }

        return VoteList
    }

    fun getStarCoun3tById(productId: Int): Int {
        val allVoteShops = getAllVoteProduct()
        return allVoteShops.count { it._idProduct == productId && it.numberofstart == "4" }
    }

    fun getByVote3ShopId(shopId: Int): List<VoteProductModel> {
        val VoteList = mutableListOf<VoteProductModel>()

        val allvote = getAllVoteProduct()

        for (vote in allvote) {
            if (vote._idProduct == shopId && vote.numberofstart == "4") {
                VoteList.add(vote)
            }
        }

        return VoteList
    }

    fun getStarCoun4tById(shopId: Int): Int {
        val allVoteShops = getAllVoteProduct()
        return allVoteShops.count { it._idProduct == shopId && it.numberofstart == "5" }
    }

    fun getByVote4ShopId(shopId: Int): List<VoteProductModel> {
        val VoteList = mutableListOf<VoteProductModel>()

        val allvote = getAllVoteProduct()

        for (vote in allvote) {
            if (vote._idProduct == shopId && vote.numberofstart == "5") {
                VoteList.add(vote)
            }
        }

        return VoteList
    }

    fun getStarCountById(shopId: Int): Int {
        val allVoteShops = getAllVoteProduct()
        return allVoteShops.count { it._idProduct == shopId && it.numberofstart == "1" }
    }

    // tổng vote
    fun getVoteShopCountById(shopId: Int): Int {
        val allVoteShops = getAllVoteProduct()
        return allVoteShops.count { it._idProduct == shopId }
    }

    //    ----------------- thêm voteshop -------------------
    fun addVoteProduct(voteshop: VoteProductModel): Long {
        val db = sqLiteData.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("numberofstart", voteshop.numberofstart)
        contentValues.put("content", voteshop.content)
        contentValues.put("date", voteshop.date)
        contentValues.put("_idUser", voteshop._idUser)
        contentValues.put("_idProduct", voteshop._idProduct)

        val addvoteshop = db.insert("VOTEPRODUCT", null, contentValues)
        db.close()

        return addvoteshop
    }
    fun calculateAverageRatingByShopId(ProductId: Int): Double {
        val voteShopList = getByVoteProductIdabc(ProductId)
        if (voteShopList.isEmpty()) return 0.0

        val sum = voteShopList.sumBy { it.numberofstart.toIntOrNull() ?: 0 }
        return sum.toDouble() / voteShopList.size
    }


}