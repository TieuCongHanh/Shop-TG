package com.example.appbanhangtg.DAO

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.appbanhangtg.Model.BillModel
import com.example.appbanhangtg.Model.CartModel
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.SQLiteDatabase.SQLiteData

class CartDAO (private val context: Context) {

    private var sqLiteData: SQLiteData = SQLiteData(context)

    @SuppressLint("Range")
    fun getAllBill(): List<CartModel> {
        val cartList = mutableListOf<CartModel>()
        val db = sqLiteData.readableDatabase
        val cursor: Cursor? = db.query(
            "CART", null, null, null,
            null, null, null, null
        )
        cursor?.use {
            while (it.moveToNext()) {
                val _idCart = it.getInt(it.getColumnIndex("_idCart"))
                val _idUser = it.getInt(it.getColumnIndex("_idUser"))
                val _idProduct = it.getInt(it.getColumnIndex("_idProduct"))
                val _idShop = it.getInt(it.getColumnIndex("_idShop"))
                val cart = CartModel(
                    _idCart,
                    _idUser,
                    _idProduct,
                    _idShop
                )
                cartList.add(cart)
            }
        }
        cursor?.close()
        db.close()
        return cartList
    }
    @SuppressLint("Range")
    fun getByCartIdUser(userId: Int): List<CartModel> {
        val cartList = mutableListOf<CartModel>()
        val db = sqLiteData.readableDatabase

        val selection = "_idUser = ?"
        val selectionArgs = arrayOf(userId.toString())

        val cursor: Cursor? = db.query(
            "CART", null, selection, selectionArgs,
            null, null, null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val _idCart = it.getInt(it.getColumnIndex("_idCart"))
                val _idUser = it.getInt(it.getColumnIndex("_idUser"))
                val _idProduct = it.getInt(it.getColumnIndex("_idProduct"))
                val _idShop = it.getInt(it.getColumnIndex("_idShop"))
                val cart = CartModel(
                    _idCart,
                    _idUser,
                    _idProduct,
                    _idShop
                )
                cartList.add(cart)
            }
        }

        cursor?.close()
        db.close()

        return cartList
    }

    @SuppressLint("Range")
    fun getProductInfoByCartId(productId: Int): Triple<String?, Double?, String?> {
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


    fun getCartCountByUserId(userId: Int): Int {
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

    fun addCart(cart: CartModel): Long {
        val db = sqLiteData.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("_idUser", cart._idUser)
        contentValues.put("_idProduct", cart._idProduct)
        contentValues.put("_idShop", cart._idShop)

        val addcart = db.insert("CART", null, contentValues)
        db.close()
        return addcart
    }
    fun deleteCart(cartId: Int): Int {
        val db = sqLiteData.writableDatabase
        val deleteCount = db.delete("CART", "_idCart = ?", arrayOf(cartId.toString()))
        db.close()
        return deleteCount
    }

}