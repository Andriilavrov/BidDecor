package com.example.biddecor.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

//    public fun loadUser (email: String){
//        MainActivity.userEmail
//        val db = DbHelper(context, null)
//    }


    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}