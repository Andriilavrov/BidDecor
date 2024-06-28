package com.example.biddecor

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.biddecor.model.Bid
import com.example.biddecor.model.User
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LotActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lot)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val category: TextView = findViewById(R.id.lotCategory)
        val title: TextView = findViewById(R.id.lotTitle)
        val price: TextView = findViewById(R.id.lotCurrentPrice)
        val deadline: TextView = findViewById(R.id.lotDeadline)
        val bidButton: Button = findViewById(R.id.bidButton)
        val description: TextView = findViewById(R.id.lotDescriprion)
        val exitBtn: Button = findViewById(R.id.lotExitButton)
        val image: ImageView = findViewById(R.id.imageView2)

        category.text = intent.getStringExtra("lotCategory")
        title.text = intent.getStringExtra("lotTitle")
        price.text = intent.getStringExtra("lotStartPrice") + " ₴"
        deadline.text = "Кінець: " + intent.getStringExtra("lotDeadline")
        description.text = intent.getStringExtra("lotDesc")
        val lotId: Int = intent.getIntExtra("lotId", -1)

        val resId =
            resources.getIdentifier(intent.getStringExtra("imageRef"), "drawable", packageName)
        image.setImageResource(resId)

        fun getCurrentDateTime(): String {
            val date = Date()
            val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
            return formatter.format(date)
        }

        bidButton.setOnClickListener {
            val editTextNumber: EditText = findViewById(R.id.editTextNumber)
            val preferrefBid: String = editTextNumber.text.toString().trim()
            val bidValue: Int = preferrefBid.toInt()
            val currentValue: Int = price.text.toString().trim().toInt()
            if (bidValue < currentValue) {
                Toast.makeText(
                    this,
                    "Неможливо зробити ставку, нижчу за поточну ціну!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                price.text = preferrefBid + " ₴"
                editTextNumber.text.clear()
                val db = DbHelper(this, null)
                val bid = Bid(
                    bidId = null,
                    bidDate = getCurrentDateTime(),
                    bidValue = bidValue,
                    costumerId = 0,
                    lotId = lotId
                )
                db.addBid(bid)
            }
        }

        exitBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val db = DbHelper(this, null)
        val jsonFilePath = File(filesDir, "user.json")
        val jsonString = jsonFilePath.readText()
        val jsonObject = JSONObject(jsonString)
        val email = jsonObject.getString("email")
        val user: User? = db.getUserByEmail(email)

        val checkBox = findViewById<CheckBox>(R.id.checkBox)

        val favoriteId = favId(user?.userId, lotId)

        if (favoriteId != -1){
            checkBox.isChecked = true
        }

        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val insertedId = db.insertFavorite(user?.userId, lotId)
                if (insertedId != -1) {
                    Toast.makeText(this, "Лот було збережено", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Помилка при збережені лоту", Toast.LENGTH_SHORT).show()
                }
            } else {
                db.removeFavorite(favoriteId)
            }
        }
    }

    fun favId(userId: Int?, lotId: Int): Int {
        if (userId == null){
            return -1
        }
        val database = DbHelper(this, null)
        val db = database.readableDatabase
        val query = "SELECT favoriteId FROM Favorite WHERE userId = ? AND lotId = ?"
        val cursor = db.rawQuery(query, arrayOf(userId.toString(), lotId.toString()))

        var favoriteId: Int = -1

        if (cursor.moveToFirst()) {
            val colInd = cursor.getColumnIndex("favoriteId")
            favoriteId = cursor.getInt(colInd)
        }

        cursor.close()
        db.close()
        return favoriteId
    }
}