package com.example.appbanhangtg.Interface

import android.content.Context
import android.content.SharedPreferences
import com.example.appbanhangtg.Model.UserModel
import com.google.gson.Gson

object SharedPrefsManager {
    private const val PREF_NAME = "MyAppPrefs"
    private const val USER_KEY = "currentUser"

    fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveUser(context: Context, user: UserModel?) {
        val editor = getPrefs(context).edit()
        val gson = Gson()
        val userJson = gson.toJson(user)
        editor.putString(USER_KEY, userJson)
        editor.apply()
    }

    fun getUser(context: Context): UserModel? {
        val gson = Gson()
        val userJson = getPrefs(context).getString(USER_KEY, null)
        return gson.fromJson(userJson, UserModel::class.java)
    }

    fun clearUser(context: Context) {
        val editor = getPrefs(context).edit()
        editor.remove(USER_KEY)
        editor.apply()
    }

}