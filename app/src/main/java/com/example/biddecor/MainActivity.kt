package com.example.biddecor

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.biddecor.databinding.ActivityMainBinding
import com.example.biddecor.model.User
import com.example.biddecor.network.RetrofitClient
import com.example.biddecor.network.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val retrofit = RetrofitClient.instance
        userService = retrofit.create(UserService::class.java)

        val button: Button = findViewById(R.id.confirm_button)

        button.setOnClickListener{
            fetchAllUsers()
        }



    }



    private fun fetchAllUsers() {
        val call = userService.getAllUsers()
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    // Обробка отриманих даних (наприклад, відображення у RecyclerView)
                    users?.let {
                        for (user in it) {
                            println("User: $user")
                        }
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Не вдалося отримати дані", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Сталася помилка при отриманні даних", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }
        })
    }
}