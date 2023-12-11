package com.example.appbanhangtg.Model

import java.io.Serializable

class AddressModel(
    val _idAddRess:Int,
    val fullname:String,
    val phone:String,
    val address:String,
    val note:String,
    val _idUser:Int,

) : Serializable {
}