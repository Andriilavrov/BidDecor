package com.example.biddecor.network

import retrofit2.Call
import retrofit2.http.GET
import com.example.biddecor.model.User

interface UserService {
    @GET("/Users")
    fun getAllUsers(): Call<List<User>>
}