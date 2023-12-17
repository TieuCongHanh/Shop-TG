package com.example.appbanhangtg.Model

import java.io.Serializable

class VoteProductModel (
    val _idVoteProduct:Int,
    val numberofstart:String,
    val content:String,
    val date:String,
    val _idUser:Int,
    val _idProduct:Int,
    ) : Serializable {
}