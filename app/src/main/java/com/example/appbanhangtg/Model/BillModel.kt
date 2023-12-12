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
    val TTXacNhan : String,
    val TTLayhang : String,
    val TTGiaoHang : String,
    val TTHuy : String,
    val TTDaGiao : String,
    val TTVote : String,
    val _idUser : Int,
    val _idProduct : Int,
    val _idAddRess : Int,
): Serializable {


}