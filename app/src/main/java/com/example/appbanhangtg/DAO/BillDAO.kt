package com.example.appbanhangtg.DAO

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.appbanhangtg.Model.AddressModel
import com.example.appbanhangtg.Model.BillModel
import com.example.appbanhangtg.Model.ProductModel

import com.example.appbanhangtg.SQLiteDatabase.SQLiteData

class BillDAO(private val context: Context) {

    private var sqLiteData: SQLiteData = SQLiteData(context)

    @SuppressLint("Range")
    fun getAllBill(): List<BillModel> {
        val billtList = mutableListOf<BillModel>()
        val db = sqLiteData.readableDatabase
        val cursor: Cursor? = db.query(
            "BILL", null, null, null,
            null, null, null, null
        )
        cursor?.use {
            while (it.moveToNext()) {
                val _idBill = it.getInt(it.getColumnIndex("_idBill"))
                val quantitybill = it.getInt(it.getColumnIndex("quantitybill"))
                val sumpricebill = it.getDouble(it.getColumnIndex("sumpricebill"))
                val ptthanhtoan = it.getString(it.getColumnIndex("ptthanhtoan"))
                val phiship = it.getDouble(it.getColumnIndex("phiship"))
                val datedathang = it.getString(it.getColumnIndex("datedathang"))
                val datenhanhang = it.getString(it.getColumnIndex("datenhanhang"))
                val TTdathang = it.getString(it.getColumnIndex("TTdathang"))
                val TTproduct = it.getString(it.getColumnIndex("TTproduct"))
                val TTgiaohang = it.getString(it.getColumnIndex("TTgiaohang"))
                val TThuy = it.getString(it.getColumnIndex("TThuy"))
                val TTNH = it.getString(it.getColumnIndex("TTNH"))
                val TTvote = it.getString(it.getColumnIndex("TTvote"))
                val _idUser = it.getInt(it.getColumnIndex("_idUser"))
                val _idProduct = it.getInt(it.getColumnIndex("_idProduct"))
                val _idAddRess = it.getInt(it.getColumnIndex("_idAddRess"))
                val product = BillModel(
                    _idBill,
                    quantitybill,
                    sumpricebill,
                    ptthanhtoan,
                    phiship,
                    datedathang,
                    datenhanhang,
                    TTdathang,
                    TTproduct,
                    TTgiaohang,
                    TThuy,
                    TTNH,
                    TTvote,
                    _idUser,
                    _idProduct,
                    _idAddRess
                )
                billtList.add(product)
            }
        }
        cursor?.close()
        db.close()
        return billtList
    }
    fun getByBillIdUser(userId: Int): List<BillModel> {
        val billList = mutableListOf<BillModel>()
        val db = sqLiteData.readableDatabase

        val selection = "_idUser = ?"
        val selectionArgs = arrayOf(userId.toString())

        val cursor: Cursor? = db.query(
            "BILL", null, selection, selectionArgs,
            null, null, null
        )

        cursor?.use {
            while (it.moveToNext()) {
                // Lấy dữ liệu từ Cursor và thêm vào billList
            }
        }

        cursor?.close()
        db.close()

        return billList
    }

    @SuppressLint("Range")
    fun getProductInfoByBillId(productId: Int): Triple<String?, Double?, String?> {
        var nameProduct: String? = null
        var priceProduct: Double? = null
        var imageProduct: String? = null
        val db = sqLiteData.readableDatabase

        val selection = "_idProduct = ?"
        val selectionArgs = arrayOf(productId.toString())

        val cursor: Cursor? = db.query(
            "PRODUCT", arrayOf("nameProduct", "priceProduct","imageProduct"), selection, selectionArgs,
            null, null, null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                nameProduct = it.getString(it.getColumnIndex("nameProduct"))
                priceProduct = it.getDouble(it.getColumnIndex("priceProduct"))
                imageProduct = it.getString(it.getColumnIndex("imageProduct"))
            }
        }

        cursor?.close()
        db.close()

        return Triple(nameProduct, priceProduct, imageProduct)
    }



    fun addBill(bill: BillModel): Long {
        val db = sqLiteData.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("quantitybill", bill.quantitybill)
        contentValues.put("sumpricebill", bill.sumpricebill)
        contentValues.put("ptthanhtoan", bill.ptthanhtoan)
        contentValues.put("phiship", bill.phiship)
        contentValues.put("datedathang", bill.datedathang)
        contentValues.put("datenhanhang", bill.datenhanhang)
        contentValues.put("TTdathang", bill.TTdathang)
        contentValues.put("TTproduct", bill.TTproduct)
        contentValues.put("TTgiaohang", bill.TTgiaohang)
        contentValues.put("TThuy", bill.TThuy)
        contentValues.put("TTNH", bill.TTNH)
        contentValues.put("TTvote", bill.TTvote)
        contentValues.put("_idUser", bill._idUser)
        contentValues.put("_idProduct", bill._idProduct)
        contentValues.put("_idAddRess", bill._idAddRess)

        val addproduct = db.insert("BILL", null, contentValues)
        db.close()
        return addproduct
    }

}