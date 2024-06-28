package com.example.biddecor.model

class User(
    var userId: Int?,
    val userName: String,
    val email: String,
    val password: String,
    val ImageProfileRef: String? = null
) {

}