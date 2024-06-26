package com.example.biddecor.model

import java.time.LocalDateTime

class Lot(
    val lotId: Int?,
    val userId: Int,
    val sratrPrice: Double,
    val buyOutPrice: Double,
    val description: String,
    val deadline: LocalDateTime, val category: String,
    val ImageDataRef: String
) {

}