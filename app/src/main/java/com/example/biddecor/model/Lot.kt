package com.example.biddecor.model

import java.time.LocalDateTime

class Lot(
    val lotId: Int?,
    val userId: Int,
    val startPrice: Double,
    val buyOutPrice: Double?,
    val description: String,
    val deadline: String,
    val category: String,
    val ImageDataRef: String
) {

}