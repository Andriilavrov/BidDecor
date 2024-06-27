package com.example.biddecor

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.biddecor.model.Bid

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

        bidButton.setOnClickListener {
            val editTextNumber: EditText = findViewById(R.id.editTextNumber)
            val bidCost: String = editTextNumber.text.toString().trim()
            val bidCostInt: Int = bidCost.toInt()


            price.text = bidCost + " ₴"
            editTextNumber.text.clear()

            //TODO
        }

        exitBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}