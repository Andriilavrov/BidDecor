package com.example.biddecor

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.biddecor.databinding.ActivityMainBinding
import android.widget.TextView

import com.example.biddecor.ui.DataBase_test
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val jdbcUrl = "jdbc:sqlserver://biddecor.database.windows.net:1433;database=BidDecor_v1;user=lavrov@biddecor;password={your_password_here};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;"

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        val textView2: TextView = findViewById(R.id.textView2)
//
//        CoroutineScope(Dispatchers.IO).launch {
//            val result = fetchDataFromDatabase()
//            withContext(Dispatchers.Main) {
//                textView2.text = result
//            }
//        }
//    }

    private fun fetchDataFromDatabase(): String {
        var connection: Connection? = null
        var result: String = ""

        try {
            // Завантажуємо драйвер
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")

            // Створюємо підключення
            connection = DriverManager.getConnection(jdbcUrl)

            // Виконуємо запит
            val statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT TOP 1 * FROM YourTable")

            if (resultSet.next()) {
                result = resultSet.getString("YourColumn")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            result = "Error: ${e.message}"
        } finally {
            connection?.close()
        }

        return result
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView2: TextView = findViewById(R.id.textView2)

        CoroutineScope(Dispatchers.IO).launch {
            val result = fetchDataFromDatabase()
            withContext(Dispatchers.Main) {
                textView2.text = result
            }
        }


//        super.onCreate(savedInstanceState)

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

//        val textView2 = findViewById<TextView>(R.id.text_home)

        DataBase_test { result ->
            textView2.text = result
        }.execute()
    }
}