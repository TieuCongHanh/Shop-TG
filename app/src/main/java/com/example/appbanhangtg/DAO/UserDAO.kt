package com.example.appbanhangtg.DAO

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.Model.UserModel
import com.example.appbanhangtg.Model.VoteShopModel

import com.example.appbanhangtg.SQLiteDatabase.SQLiteData

class UserDAO (context: Context){
    private var sqLiteData: SQLiteData = SQLiteData(context)

    @SuppressLint("Range")
    fun getAllUsers(): List<UserModel> {
        val userList = mutableListOf<UserModel>()
        val db = sqLiteData.readableDatabase
        val cursor: Cursor? = db.query(
            "USER", null, null, null,
            null, null, null, null
        )
        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndex("_idUser"))
                val username = it.getString(it.getColumnIndex("username"))
                val password = it.getString(it.getColumnIndex("password"))
                val phone = it.getString(it.getColumnIndex("phone"))
                val role = it.getString(it.getColumnIndex("role"))
                val email = it.getString(it.getColumnIndex("email"))
                val image = it.getString(it.getColumnIndex("image"))
                val user = UserModel(id, username, password, phone, role, email, image)
                userList.add(user)
            }
        }
        cursor?.close()
        db.close()
        return userList
    }
    fun addUser(user: UserModel): Long {
        val db = sqLiteData.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", user.username)
        contentValues.put("password", user.password)
        contentValues.put("phone", user.phone)
        contentValues.put("role", user.role)
        contentValues.put("email", user.email)
        contentValues.put("image", user.image ?: "")

        val adduser = db.insert("USER", null, contentValues)
        db.close()
        return adduser
    }
    fun updateUser(user: UserModel): Int {
        val db = sqLiteData.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", user.username)
        contentValues.put("password", user.password)
        contentValues.put("phone", user.phone)
        contentValues.put("role", user.role)
        contentValues.put("email", user.email)
        contentValues.put("image", user.image ?: "")

        val updateuser = db.update("USER", contentValues, "_idUser = ?", arrayOf(user._idUser.toString()))
        db.close()
        return updateuser
    }
    fun updateUserDetails(userId: Int, phone: String,email: String, image: String?): Int {
        val db = sqLiteData.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("phone", phone)
        contentValues.put("email", email)
        contentValues.put("image", image ?: "")

        val updateCount = db.update("USER", contentValues, "_idUser = ?", arrayOf(userId.toString()))
        db.close()
        return updateCount
    }
    fun updateUserPassword(userId: Int, newPassword: String): Int {
        val db = sqLiteData.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("password", newPassword)

        val updateCount = db.update("USER", contentValues, "_idUser = ?", arrayOf(userId.toString()))
        db.close()
        return updateCount
    }
    fun updateUserRole(userId: Int, newrole: String): Int {
        val db = sqLiteData.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("role", newrole)

        val updateCount = db.update("USER", contentValues, "_idUser = ?", arrayOf(userId.toString()))
        db.close()
        return updateCount
    }

    fun login(username: String, password: String, context: Context): UserModel? {
        val userList = getAllUsers()
        val matchingUser = userList.find { it.username == username && it.password == password }

        if (matchingUser != null) {
            SharedPrefsManager.saveUser(context, matchingUser) // Lưu thông tin người dùng vào SharedPreferences
        }

        return matchingUser
    }
    private lateinit var voteShopDAO: VoteShopDAO

    @SuppressLint("Range")
    fun getUserInfoByUserId(userId: Int): Pair<String?, String?> {
        var username: String? = null
        var image: String? = null
        val db = sqLiteData.readableDatabase

        val selection = "_idUser = ?"
        val selectionArgs = arrayOf(userId.toString())

        val cursor: Cursor? = db.query(
            "USER", arrayOf("username", "image"), selection, selectionArgs,
            null, null, null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                username = it.getString(it.getColumnIndex("username"))
                image = it.getString(it.getColumnIndex("image"))
            }
        }

        cursor?.close()
        db.close()

        return Pair(username, image)
    }





    fun deleteUser(userId: Int): Int {
        val db = sqLiteData.writableDatabase
        val rowsAffected = db.delete("USER", "_idUser = ?", arrayOf(userId.toString()))
        db.close()
        return rowsAffected
    }


}