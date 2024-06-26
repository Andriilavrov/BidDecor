package com.example.biddecor

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.biddecor.model.User

class RegActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)

        val userName: EditText = findViewById(R.id.userName)
        val userEmail: EditText = findViewById(R.id.userEmail)
        val userPass: EditText = findViewById(R.id.userPassword)
        val userPassConf: EditText = findViewById(R.id.userPasswordConfirm)
        val button: Button = findViewById(R.id.regButton)

        button.setOnClickListener {
            val name = userName.text.toString().trim()
            val email = userEmail.text.toString().trim()
            val pass = userPass.text.toString().trim()
            val passConfirm = userPassConf.text.toString().trim()

            if (name == "" || email == "" || pass == "" || passConfirm == "") {
                Toast.makeText(this, "Не всі поля заповнені", Toast.LENGTH_SHORT).show()
            } else if (pass != passConfirm) {
                Toast.makeText(this, "Пароль не підтвердженно", Toast.LENGTH_SHORT).show()
            } else {
                val user = User(name, email, pass)
                val db = DbHelper(this, null)
            }
        }


    }
}