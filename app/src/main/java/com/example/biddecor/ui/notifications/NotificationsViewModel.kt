package com.example.biddecor.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.biddecor.DbHelper
import com.example.biddecor.MainActivity

class NotificationsViewModel : ViewModel() {

//    public fun loadUser (email: String){
//        MainActivity.userEmail
//        val db = DbHelper(context, null)
//    }


    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}