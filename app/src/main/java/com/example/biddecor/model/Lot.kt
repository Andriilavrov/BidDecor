package com.example.biddecor.model

import java.time.LocalDateTime

class Lot(
    val lotId: Int?,
    val ownerId: Int,
    val lastBid: Bid?,
    val startPrice: Double,
    val buyOutPrice: Double?,
    val title : String,
    val description: String,
    val deadline: String,
    val category: String,
    val ImageDataRef: String
) {

}