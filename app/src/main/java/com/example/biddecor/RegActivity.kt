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
import com.example.biddecor.model.User

class RegActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)

        val authButton = findViewById<Button>(R.id.enterButton)
        authButton.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        val userName: EditText = findViewById(R.id.userName)
        val userEmail: EditText = findViewById(R.id.userEmail)
        val userPass: EditText = findViewById(R.id.userPassword)
        val userPassConf: EditText = findViewById(R.id.userPasswordConfirm)
        val button: Button = findViewById(R.id.regButton)

        val regButton = findViewById<Button>(R.id.regButton)
        regButton.setOnClickListener {
            val name = userName.text.toString().trim()
            val email = userEmail.text.toString().trim()
            val pass = userPass.text.toString().trim()
            val passConfirm = userPassConf.text.toString().trim()

            if (name == "" || email == "" || pass == "" || passConfirm == "") {
                Toast.makeText(this, "Не всі поля заповнені", Toast.LENGTH_SHORT).show()
            } else if (pass != passConfirm) {
                Toast.makeText(this, "Пароль не підтвердженно", Toast.LENGTH_SHORT).show()
            } else {
                val user = User(null, name, email, pass, null)

                val db = DbHelper(this, null)
                db.addUser(user)
                Toast.makeText(this, "Користувач $name зареєстрований", Toast.LENGTH_SHORT).show()

                userName.text.clear()
                userEmail.text.clear()
                userPass.text.clear()
                userPassConf.text.clear()
            }
        }


    }
}