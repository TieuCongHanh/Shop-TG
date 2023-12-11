package com.example.appbanhangtg.Model

import java.io.Serializable

class ProductModel(
    val _idProduct : Int,
    val nameProduct: String,
    val quantityProduct: Int,
    val priceProduct: Double,
    val descriptionProduct:String,
    val imageProduct:String,
    val _idUser :Int,
    val _idShop:Int,
    val _idtypeProduct: Int
) : Serializable {
}