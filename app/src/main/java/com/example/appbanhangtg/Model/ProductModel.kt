package com.example.appbanhangtg.Model

import java.io.Serializable

class ProductModel(
    val _idProduct : Int,
    val nameProduct: String,
    val quantityProduct: String,
    val priceProduct: String,
    val descriptionProduct:String,
    val imageProduct:String,
    val _idUser :Int,
    val _idShop:Int,
    val _idtypeProduct: Int
) : Serializable {
}