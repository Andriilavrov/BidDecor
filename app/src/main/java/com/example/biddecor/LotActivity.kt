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
        }

        exitBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val checkBox = findViewById<CheckBox>(R.id.checkBox)
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val db = DbHelper(this, null)
                val jsonFilePath = File(filesDir, "user.json")
                val jsonString = jsonFilePath.readText()
                val jsonObject = JSONObject(jsonString)
                val email = jsonObject.getString("email")
                val user: User? = db.getUserByEmail(email)
                val insertedId = db.insertFavorite(user?.userId, lotId)
                if (insertedId != -1) {
                    Toast.makeText(this, "Лот було збережено", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Помилка при збережені лоту", Toast.LENGTH_SHORT).show()
                }
            } else {
                removeFavorite()
            }
        }
    }

    fun addFavorite() {
        val dbHelper = DbHelper(this, null)
        val db = dbHelper.writableDatabase

        val contentValues = ContentValues().apply {
            put("column_name", "value")
        }

        db.insert("table_name", null, contentValues)
        db.close()
    }

    fun removeFavorite() {
        val dbHelper = DbHelper(this, null)
        val db = dbHelper.writableDatabase

        // Замените условие на нужное, чтобы удалить правильную запись
        val selection = "column_name = ?"
        val selectionArgs = arrayOf("value")

        db.delete("table_name", selection, selectionArgs)
        db.close()
    }
}