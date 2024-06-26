package com.example.biddecor.model

import java.time.LocalDateTime

class Bid(
    val bidId: Int,
    val bidDate: LocalDateTime,
    val vidValue: Double,
    val userId: Int,
    val lotId: Int
) {

}