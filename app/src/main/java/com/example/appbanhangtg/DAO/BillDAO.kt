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
                val TTXacNhan = it.getString(it.getColumnIndex("TTXacNhan"))
                val TTLayhang = it.getString(it.getColumnIndex("TTLayhang"))
                val TTGiaoHang = it.getString(it.getColumnIndex("TTGiaoHang"))
                val TTHuy = it.getString(it.getColumnIndex("TTHuy"))
                val TTDaGiao = it.getString(it.getColumnIndex("TTDaGiao"))
                val TTVote = it.getString(it.getColumnIndex("TTVote"))
                val _idUser = it.getInt(it.getColumnIndex("_idUser"))
                val _idProduct = it.getInt(it.getColumnIndex("_idProduct"))
                val _idAddRess = it.getInt(it.getColumnIndex("_idAddRess"))
                val _idShop = it.getInt(it.getColumnIndex("_idShop"))
                val username = it.getString(it.getColumnIndex("username"))
                val product = BillModel(
                    _idBill,
                    quantitybill,
                    sumpricebill,
                    ptthanhtoan,
                    phiship,
                    datedathang,
                    datenhanhang,
                    TTXacNhan,
                    TTLayhang,
                    TTGiaoHang,
                    TTHuy,
                    TTDaGiao,
                    TTVote,
                    _idUser,
                    _idProduct,
                    _idAddRess,
                    _idShop,
                    username
                )
                billtList.add(product)
            }
        }
        cursor?.close()
        db.close()
        return billtList
    }

    @SuppressLint("Range")
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
                val _idBill = it.getInt(it.getColumnIndex("_idBill"))
                val quantitybill = it.getInt(it.getColumnIndex("quantitybill"))
                val sumpricebill = it.getDouble(it.getColumnIndex("sumpricebill"))
                val ptthanhtoan = it.getString(it.getColumnIndex("ptthanhtoan"))
                val phiship = it.getDouble(it.getColumnIndex("phiship"))
                val datedathang = it.getString(it.getColumnIndex("datedathang"))
                val datenhanhang = it.getString(it.getColumnIndex("datenhanhang"))
                val TTXacNhan = it.getString(it.getColumnIndex("TTXacNhan"))
                val TTLayhang = it.getString(it.getColumnIndex("TTLayhang"))
                val TTGiaoHang = it.getString(it.getColumnIndex("TTGiaoHang"))
                val TTHuy = it.getString(it.getColumnIndex("TTHuy"))
                val TTDaGiao = it.getString(it.getColumnIndex("TTDaGiao"))
                val TTVote = it.getString(it.getColumnIndex("TTVote"))
                val _idUser = it.getInt(it.getColumnIndex("_idUser"))
                val _idProduct = it.getInt(it.getColumnIndex("_idProduct"))
                val _idAddRess = it.getInt(it.getColumnIndex("_idAddRess"))
                val _idShop = it.getInt(it.getColumnIndex("_idShop"))
                val username = it.getString(it.getColumnIndex("username"))
                val bill = BillModel(
                    _idBill,
                    quantitybill,
                    sumpricebill,
                    ptthanhtoan,
                    phiship,
                    datedathang,
                    datenhanhang,
                    TTXacNhan,
                    TTLayhang,
                    TTGiaoHang,
                    TTHuy,
                    TTDaGiao,
                    TTVote,
                    _idUser,
                    _idProduct,
                    _idAddRess,
                    _idShop,
                    username
                )
                billList.add(bill)
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
                "PRODUCT",
                arrayOf("nameProduct", "priceProduct", "imageProduct"),
                selection,
                selectionArgs,
                null,
                null,
                null
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

        fun getbillCountByUserId(userId: Int): Int {
            val allProducts = getAllBill()
            return allProducts.count { it._idUser == userId }
        }

        @SuppressLint("Range")
        fun getProductByIdProduct(productId: Int): ProductModel? {
            val db = sqLiteData.readableDatabase

            val selection = "_idProduct = ?"
            val selectionArgs = arrayOf(productId.toString())

            val cursor: Cursor? = db.query(
                "PRODUCT", null, selection, selectionArgs,
                null, null, null
            )

            var product: ProductModel? = null

            cursor?.use {
                if (it.moveToFirst()) {
                    val _idProduct = it.getInt(it.getColumnIndex("_idProduct"))
                    val nameProduct = it.getString(it.getColumnIndex("nameProduct"))
                    val quantityProduct = it.getInt(it.getColumnIndex("quantityProduct"))
                    val priceProduct = it.getDouble(it.getColumnIndex("priceProduct"))
                    val descriptionProduct = it.getString(it.getColumnIndex("descriptionProduct"))
                    val imageProduct = it.getString(it.getColumnIndex("imageProduct"))
                    val _idUser = it.getInt(it.getColumnIndex("_idUser"))
                    val _idShop = it.getInt(it.getColumnIndex("_idShop"))
                    val _idtypeProduct = it.getInt(it.getColumnIndex("_idtypeProduct"))

                    product = ProductModel(
                        _idProduct,
                        nameProduct,
                        quantityProduct,
                        priceProduct,
                        descriptionProduct,
                        imageProduct,
                        _idUser,
                        _idShop,
                        _idtypeProduct
                    )
                }
            }

            cursor?.close()
            db.close()

            return product
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
            contentValues.put("TTXacNhan", bill.TTXacNhan)
            contentValues.put("TTLayhang", bill.TTLayhang)
            contentValues.put("TTGiaoHang", bill.TTGiaoHang)
            contentValues.put("TTHuy", bill.TTHuy)
            contentValues.put("TTDaGiao", bill.TTDaGiao)
            contentValues.put("TTVote", bill.TTVote)
            contentValues.put("_idUser", bill._idUser)
            contentValues.put("_idProduct", bill._idProduct)
            contentValues.put("_idAddRess", bill._idAddRess)
            contentValues.put("_idShop", bill._idShop)
            contentValues.put("username", bill._idShop)

            val addproduct = db.insert("BILL", null, contentValues)
            db.close()
            return addproduct
        }
    fun updateTTXacNhan(billId: Int, newTTXacNhan: String): Int {
        val db = sqLiteData.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("TTXacNhan", newTTXacNhan)

        val selection = "_idBill = ?"
        val selectionArgs = arrayOf(billId.toString())

        val updatedRows = db.update("BILL", contentValues, selection, selectionArgs)
        db.close()
        return updatedRows
    }
    fun updateTTLayHang(billId: Int, newTTLayHang: String): Int {
        val db = sqLiteData.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("TTLayHang", newTTLayHang)

        val selection = "_idBill = ?"
        val selectionArgs = arrayOf(billId.toString())

        val updatedRows = db.update("BILL", contentValues, selection, selectionArgs)
        db.close()
        return updatedRows
    }
    fun updateTTHuy(billId: Int, newTTHuy: String): Int {
        val db = sqLiteData.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("TTHuy", newTTHuy)

        val selection = "_idBill = ?"
        val selectionArgs = arrayOf(billId.toString())

        val updatedRows = db.update("BILL", contentValues, selection, selectionArgs)
        db.close()
        return updatedRows
    }
    fun updateTTGiaoHang(billId: Int, newTTHuy: String): Int {
        val db = sqLiteData.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("TTGiaoHang", newTTHuy)

        val selection = "_idBill = ?"
        val selectionArgs = arrayOf(billId.toString())

        val updatedRows = db.update("BILL", contentValues, selection, selectionArgs)
        db.close()
        return updatedRows
    }
    fun updateDateNhan(billId: Int, newdate: String): Int {
        val db = sqLiteData.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("datenhanhang", newdate)

        val selection = "_idBill = ?"
        val selectionArgs = arrayOf(billId.toString())

        val updatedRows = db.update("BILL", contentValues, selection, selectionArgs)
        db.close()
        return updatedRows
    }
    fun updateTTDaGiao(billId: Int, newTTHuy: String): Int {
        val db = sqLiteData.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("TTDaGiao", newTTHuy)

        val selection = "_idBill = ?"
        val selectionArgs = arrayOf(billId.toString())

        val updatedRows = db.update("BILL", contentValues, selection, selectionArgs)
        db.close()
        return updatedRows
    }
    fun updateUsername(billId: Int, newTTHuy: String): Int {
        val db = sqLiteData.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", newTTHuy)

        val selection = "_idBill = ?"
        val selectionArgs = arrayOf(billId.toString())

        val updatedRows = db.update("BILL", contentValues, selection, selectionArgs)
        db.close()
        return updatedRows
    }

    }
