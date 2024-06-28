package com.example.biddecor.model

class Lot(
    val lotId: Int?,
    val ownerId: Int,
    var lastBid: Int?,
    val startPrice: Int,
    val buyOutPrice: Int?,
    val title : String,
    val description: String,
    val deadline: String,
    val category: String,
    val ImageDataRef: String
) {

}