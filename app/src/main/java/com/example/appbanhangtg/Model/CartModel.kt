package com.example.appbanhangtg.Model

import java.io.Serializable

class CartModel(
    val _idCart : Int,
    val _idUser : Int,
    val _idProduct : Int,
    val _idShop : Int
): Serializable {
}