package com.example.appbanhangtg.Model

import java.io.Serializable

class VoteShopModel(
    val _idVoteShop:Int,
    val numberofstart:String,
    val content:String,
    val date:String,
    val _idUser:Int,
    val _idShop:Int,
) : Serializable {
}