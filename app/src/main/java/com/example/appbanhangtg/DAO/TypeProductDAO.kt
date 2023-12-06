package com.example.appbanhangtg.DAO

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import com.example.appbanhangtg.Model.TypeProductModel
import com.example.appbanhangtg.Model.UserModel
import com.example.appbanhangtg.SQLiteDatabase.SQLiteData

class TypeProductDAO (context: Context) {
    private var sqLiteData: SQLiteData = SQLiteData(context)

    @SuppressLint("Range")
    fun getAllTypeProducts(): List<TypeProductModel> {
        val typeProductList = mutableListOf<TypeProductModel>()
        val db = sqLiteData.readableDatabase
        val cursor: Cursor? = db.query(
            "TYPEPRODUCT", null, null, null,
            null, null, null, null
        )
        cursor?.use {
            while (it.moveToNext()) {
                val _idtypeProduct = it.getInt(it.getColumnIndex("_idtypeProduct"))
                val nameTypeProduct = it.getString(it.getColumnIndex("nameTypeProduct"))

                val user = TypeProductModel(_idtypeProduct, nameTypeProduct)
                typeProductList.add(user)
            }
        }
        cursor?.close()
        db.close()
        return typeProductList
    }
}