package com.example.biddecor

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.common.SignInButton
import android.widget.Toast

class AuthActivity : AppCompatActivity() {
    private lateinit var googleSignInHelper: GoogleSignInHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val registerButton = findViewById<Button>(R.id.regButton)
        registerButton.setOnClickListener {
            val intent = Intent(this, RegActivity::class.java)
            startActivity(intent)
        }

        googleSignInHelper = GoogleSignInHelper(this)
        findViewById<SignInButton>(R.id.googleButton).setOnClickListener {
            googleSignInHelper.signIn(this)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        googleSignInHelper.handleSignInResult(data,
            onSuccess = { account ->
                // Handle successful sign-in
                Toast.makeText(this, "Signed in as: ${account.displayName}", Toast.LENGTH_LONG).show()
            },
            onFailure = { exception ->
                // Handle sign-in failure
                Toast.makeText(this, "Sign-in failed: ${exception.message}", Toast.LENGTH_LONG).show()
            })
    }
}