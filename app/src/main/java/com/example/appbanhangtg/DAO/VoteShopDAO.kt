package com.example.appbanhangtg.DAO

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.Model.UserModel
import com.example.appbanhangtg.Model.VoteShopModel
import com.example.appbanhangtg.SQLiteDatabase.SQLiteData

class VoteShopDAO(private val context: Context) {
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
                val product =
                    VoteShopModel(_idVoteShop, numberofstart, content, date, _idUser, _idShop)
                voteShopList.add(product)
            }
        }
        cursor?.close()
        db.close()
        return voteShopList
    }

    fun getByVoteShopIdabc(shopId: Int): List<VoteShopModel> {
        val VoteList = mutableListOf<VoteShopModel>()

        val allvote = getAllVoteShop()

        for (vote in allvote) {
            if (vote._idShop == shopId) {
                VoteList.add(vote)
            }
        }

        return VoteList
    }
    fun getByVoteShopId(shopId: Int): List<VoteShopModel> {
        val VoteList = mutableListOf<VoteShopModel>()

        val allvote = getAllVoteShop()

        for (vote in allvote) {
            if (vote._idShop == shopId && vote.numberofstart == "1") {
                VoteList.add(vote)
            }
        }

        return VoteList
    }

    fun getStarCoun1tById(shopId: Int): Int {
        val allVoteShops = getAllVoteShop()
        return allVoteShops.count { it._idShop == shopId && it.numberofstart == "2" }
    }

    fun getByVote1ShopId(shopId: Int): List<VoteShopModel> {
        val VoteList = mutableListOf<VoteShopModel>()

        val allvote = getAllVoteShop()

        for (vote in allvote) {
            if (vote._idShop == shopId && vote.numberofstart == "2") {
                VoteList.add(vote)
            }
        }

        return VoteList
    }

    fun getStarCoun2tById(shopId: Int): Int {
        val allVoteShops = getAllVoteShop()
        return allVoteShops.count { it._idShop == shopId && it.numberofstart == "3" }
    }

    fun getByVote2ShopId(shopId: Int): List<VoteShopModel> {
        val VoteList = mutableListOf<VoteShopModel>()

        val allvote = getAllVoteShop()

        for (vote in allvote) {
            if (vote._idShop == shopId && vote.numberofstart == "3") {
                VoteList.add(vote)
            }
        }

        return VoteList
    }

    fun getStarCoun3tById(shopId: Int): Int {
        val allVoteShops = getAllVoteShop()
        return allVoteShops.count { it._idShop == shopId && it.numberofstart == "4" }
    }

    fun getByVote3ShopId(shopId: Int): List<VoteShopModel> {
        val VoteList = mutableListOf<VoteShopModel>()

        val allvote = getAllVoteShop()

        for (vote in allvote) {
            if (vote._idShop == shopId && vote.numberofstart == "4") {
                VoteList.add(vote)
            }
        }

        return VoteList
    }

    fun getStarCoun4tById(shopId: Int): Int {
        val allVoteShops = getAllVoteShop()
        return allVoteShops.count { it._idShop == shopId && it.numberofstart == "5" }
    }

    fun getByVote4ShopId(shopId: Int): List<VoteShopModel> {
        val VoteList = mutableListOf<VoteShopModel>()

        val allvote = getAllVoteShop()

        for (vote in allvote) {
            if (vote._idShop == shopId && vote.numberofstart == "5") {
                VoteList.add(vote)
            }
        }

        return VoteList
    }

    fun getStarCountById(shopId: Int): Int {
        val allVoteShops = getAllVoteShop()
        return allVoteShops.count { it._idShop == shopId && it.numberofstart == "1" }
    }

    // tổng vote
    fun getVoteShopCountById(shopId: Int): Int {
        val allVoteShops = getAllVoteShop()
        return allVoteShops.count { it._idShop == shopId }
    }

    //    ----------------- thêm voteshop -------------------
    fun addVoteShop(voteshop: VoteShopModel): Long {
        val db = sqLiteData.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("numberofstart", voteshop.numberofstart)
        contentValues.put("content", voteshop.content)
        contentValues.put("date", voteshop.date)
        contentValues.put("_idUser", voteshop._idUser)
        contentValues.put("_idShop", voteshop._idShop)

        val addvoteshop = db.insert("VOTESHOP", null, contentValues)
        db.close()

        Log.d("VoteShopInsert", "Inserted ID: $addvoteshop")
        return addvoteshop
    }
    fun calculateAverageRatingByShopId(shopId: Int): Double {
        val voteShopList = getByVoteShopIdabc(shopId)
        if (voteShopList.isEmpty()) return 0.0

        val sum = voteShopList.sumBy { it.numberofstart.toIntOrNull() ?: 0 }
        return sum.toDouble() / voteShopList.size
    }


}