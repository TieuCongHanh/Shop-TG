package com.example.appbanhangtg.Model

import java.io.Serializable

class BillModel(
    val _idBill : Int,
    val quantitybill : Int,
    val sumpricebill : Double,
    val ptthanhtoan : String,
    val phiship : Double,
    val datedathang : String,
    val datenhanhang : String,
    val TTdathang : String,
    val TTproduct : String,
    val TTgiaohang : String,
    val TThuy : String,
    val TTNH : String,
    val TTvote : String,
    val _idUser : Int,
    val _idProduct : Int,
    val _idAddRess : Int,
): Serializable {


}