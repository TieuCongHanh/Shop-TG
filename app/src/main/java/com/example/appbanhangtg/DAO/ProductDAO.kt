package com.example.appbanhangtg.DAO

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.appbanhangtg.Adapter.ProductAdapter
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.Model.UserModel
import com.example.appbanhangtg.SQLiteDatabase.SQLiteData

class ProductDAO(private val context: Context) {
    private var sqLiteData: SQLiteData = SQLiteData(context)
    @SuppressLint("Range")
    fun getAllProduct(): List<ProductModel> {
        val productList = mutableListOf<ProductModel>()
        val db = sqLiteData.readableDatabase
        val cursor: Cursor? = db.query(
            "PRODUCT", null, null, null,
            null, null, null, null
        )
        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndex("_idProduct"))
                val nameProduct = it.getString(it.getColumnIndex("nameProduct"))
                val quantityProduct = it.getInt(it.getColumnIndex("quantityProduct"))
                val priceProduct = it.getDouble(it.getColumnIndex("priceProduct"))
                val descriptionProduct = it.getString(it.getColumnIndex("descriptionProduct"))
                val imageProduct = it.getString(it.getColumnIndex("imageProduct"))
                val _idUser = it.getInt(it.getColumnIndex("_idUser"))
                val _idShop = it.getInt(it.getColumnIndex("_idShop"))
                val _idtypeProduct = it.getInt(it.getColumnIndex("_idtypeProduct"))
                val product = ProductModel(id, nameProduct, quantityProduct, priceProduct, descriptionProduct,imageProduct, _idUser,_idShop,_idtypeProduct)
                productList.add(product)
            }
        }
        cursor?.close()
        db.close()
        return productList
    }

    fun getByShopIdProduct(shopId: Int): List<ProductModel> {
        val productList = mutableListOf<ProductModel>()

        val allProducts = getAllProduct()

        for (product in allProducts) {
            if (product._idShop == shopId) {
                productList.add(product)

            }
        }

        return productList
    }

    fun getProductCountByShopId(shopId: Int): Int {
        val allProducts = getAllProduct()
        return allProducts.count { it._idShop == shopId }
    }

    fun addProduct(product: ProductModel): Long {
        val db = sqLiteData.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("nameProduct", product.nameProduct)
        contentValues.put("quantityProduct", product.quantityProduct)
        contentValues.put("priceProduct", product.priceProduct)
        contentValues.put("descriptionProduct", product.descriptionProduct)
        contentValues.put("imageProduct", product.imageProduct)
        contentValues.put("_idUser", product._idUser)
        contentValues.put("_idShop", product._idShop)
        contentValues.put("_idtypeProduct", product._idtypeProduct)

        val addproduct = db.insert("PRODUCT", null, contentValues)
        db.close()
        return addproduct
    }
    fun updateProduct(product: ProductModel): Int {
        val db = sqLiteData.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("nameProduct", product.nameProduct)
        contentValues.put("quantityProduct", product.quantityProduct)
        contentValues.put("priceProduct", product.priceProduct)
        contentValues.put("descriptionProduct", product.descriptionProduct)
        contentValues.put("imageProduct", product.imageProduct)
        contentValues.put("_idUser", product._idUser)
        contentValues.put("_idShop", product._idShop)
        contentValues.put("_idtypeProduct", product._idtypeProduct)

        val updateproduct = db.update("PRODUCT", contentValues, "_idProduct = ?", arrayOf(product._idProduct.toString()))
        db.close()
        return updateproduct
    }
    fun deleteProduct(productId: Int): Int {
        val db = sqLiteData.writableDatabase
        val deleteCount = db.delete("PRODUCT", "_idProduct = ?", arrayOf(productId.toString()))
        db.close()
        return deleteCount
    }

}