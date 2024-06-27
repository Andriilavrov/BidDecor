package com.example.biddecor

import android.os.Bundle
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.biddecor.databinding.ActivityMainBinding
import com.example.biddecor.model.Lot

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_auctions, R.id.navigation_favorites, R.id.navigation_profile
            )
        )
        val filterButton = findViewById<Button>(R.id.filter_btn)
        filterButton.setOnClickListener {
            //Toast.makeText(this, "ТЕСТ ФІЛЬТРА", Toast.LENGTH_SHORT).show()

        }

//        val lotsList: RecyclerView = findViewById(R.id.lotsList)
//        val lots: ArrayList<Lot> = addLots()
//
//        lotsList.layoutManager = LinearLayoutManager(this)
//        lotsList.adapter = LotsAdapter(lots, this)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun addLots(): ArrayList<Lot> {
        val db = DbHelper(this, null)
        return db.getAllLots()
    }

}