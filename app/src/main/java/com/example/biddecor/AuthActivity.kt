package com.example.biddecor

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val userEmail: EditText = findViewById(R.id.userEmail)
        val userPass: EditText = findViewById(R.id.userPassword)
        val authButton = findViewById<Button>(R.id.enterButton)

        authButton.setOnClickListener {
            val email = userEmail.text.toString().trim()
            val pass = userPass.text.toString().trim()
            if (email == "" || pass == "") {
                Toast.makeText(this, "Не всі поля заповнені", Toast.LENGTH_SHORT).show()
            } else {
                val db = DbHelper(this, null)
                val isAuth = db.getUser(email, pass)

                if (isAuth) {
                    Toast.makeText(this, "Користувач авторизован", Toast.LENGTH_SHORT).show()
                    userEmail.text.clear()
                    userPass.text.clear()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Неправильна пошта або пароль", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val registerButton = findViewById<Button>(R.id.regButton)
        registerButton.setOnClickListener {
            val intent = Intent(this, RegActivity::class.java)
            startActivity(intent)
        }
    }
}