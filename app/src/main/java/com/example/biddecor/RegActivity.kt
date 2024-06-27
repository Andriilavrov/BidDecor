package com.example.biddecor

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.biddecor.model.User
import org.json.JSONObject
import java.io.File

class RegActivity : AppCompatActivity() {
    private lateinit var googleSignInHelper: GoogleSignInHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)

        googleSignInHelper = GoogleSignInHelper(this)
        findViewById<Button>(R.id.googleButton).setOnClickListener {
            googleSignInHelper.signIn(this)
        }

        val authButton = findViewById<Button>(R.id.enterButton)
        authButton.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        val userName: EditText = findViewById(R.id.userName)
        val userEmail: EditText = findViewById(R.id.userEmail)
        val userPass: EditText = findViewById(R.id.userPassword)
        val userPassConf: EditText = findViewById(R.id.userPasswordConfirm)

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

                Toast.makeText(this, "Signed in as: ${account.displayName}", Toast.LENGTH_LONG).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            },
            onFailure = { exception ->
                Toast.makeText(this, "Sign-in failed: ${exception.message}", Toast.LENGTH_SHORT).show()
            })
    }
}