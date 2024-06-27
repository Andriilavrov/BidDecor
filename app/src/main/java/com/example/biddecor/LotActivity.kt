package com.example.biddecor

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.biddecor.model.Bid
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

        //Get info from intent
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
    }
}