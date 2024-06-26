package com.example.biddecor

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.example.biddecor.model.User
import org.json.JSONObject
import java.io.File

class AuthActivity : AppCompatActivity() {
    private lateinit var googleSignInHelper: GoogleSignInHelper

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

        googleSignInHelper = GoogleSignInHelper(this)
        findViewById<Button>(R.id.googleButton).setOnClickListener {
            googleSignInHelper.signIn(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        googleSignInHelper.handleSignInResult(data,
            onSuccess = { account ->
                val email = account.email ?: ""
                val name = account.displayName ?: ""
                val photoUrl: Uri? = account.photoUrl
                val photoUrlString = photoUrl?.toString() ?: null

                val user = User(null, name, email, "", photoUrlString)
                val db = DbHelper(this, null)
                db.addUser(user)

                val jsonObject = JSONObject()
                jsonObject.put("email", email)
                val jsonFilePath = File(filesDir, "user.json")
                jsonFilePath.writeText(jsonObject.toString())




                Toast.makeText(this, "Signed in as: ${account.displayName}", Toast.LENGTH_LONG)
                    .show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            },
            onFailure = { exception ->
                Toast.makeText(this, "Sign-in failed: ${exception.message}", Toast.LENGTH_SHORT).show()
            })
    }

}