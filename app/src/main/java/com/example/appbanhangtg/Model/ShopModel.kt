package com.example.appbanhangtg.Model

import java.io.Serializable

class ShopModel(
    val _idShop:Int,
    val nameShop:String,
    val descriptionShop:String,
    val sloganShop: String,
    val imageavtShop:String,
    val imageShop:String,
    val _idUser:Int
): Serializable // Đánh dấu implement Serializable
 {
}