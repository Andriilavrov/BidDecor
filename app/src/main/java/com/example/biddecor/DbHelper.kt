package com.example.biddecor

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.biddecor.model.Bid
import com.example.biddecor.model.Favorite
import com.example.biddecor.model.Lot
import com.example.biddecor.model.Message
import com.example.biddecor.model.User

class DbHelper(val context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "biddecor", factory, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE User (
                userId INTEGER PRIMARY KEY AUTOINCREMENT,
                userName TEXT,
                email TEXT,
                password TEXT,
                ImageProfileRef TEXT DEFAULT NULL
            );
            """
        )
        db.execSQL(
            """
            CREATE TABLE Lot (
                lotId INTEGER PRIMARY KEY AUTOINCREMENT,
                userId INTEGER,
                bidId INTEGER,
                startPrice DECIMAL(10, 2),
                buyOutPrice DECIMAL(10, 2) DEFAULT NULL,
                title TEXT,
                description TEXT,
                deadline DATETIME,
                category TEXT,
                ImageDataRef TEXT,
                FOREIGN KEY (userId) REFERENCES User(userId),
                FOREIGN KEY (bidId) REFERENCES Bid(bidId)
            );
            """
        )
        db.execSQL(
            """
            CREATE TABLE Bid (
                bidId INTEGER PRIMARY KEY AUTOINCREMENT,
                bidDate DATETIME,
                bidValue DECIMAL(10, 2),
                userId INTEGER,
                FOREIGN KEY (userId) REFERENCES User(userId)
            );
            """
        )
        db.execSQL(
            """
            CREATE TABLE Message (
                messageId INTEGER PRIMARY KEY AUTOINCREMENT,
                customerId INTEGER,
                ownerId INTEGER,
                lotId INTEGER,
                message TEXT,
                FOREIGN KEY (customerId) REFERENCES User(userId),
                FOREIGN KEY (ownerId) REFERENCES User(userId),
                FOREIGN KEY (lotId) REFERENCES Lot(lotId)
            );
            """
        )
        db.execSQL(
            """
            CREATE TABLE Favorite (
                favoriteId INTEGER PRIMARY KEY AUTOINCREMENT,
                userId INTEGER,
                lotId INTEGER,
                FOREIGN KEY (userId) REFERENCES User(userId),
                FOREIGN KEY (lotId) REFERENCES Lot(lotId)
            );
            """
        )
    }

    fun resetDatabase() {
        val db = this.writableDatabase
        onUpgrade(db, 1, 1)
        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS User")
        db.execSQL("DROP TABLE IF EXISTS Lot")
        db.execSQL("DROP TABLE IF EXISTS Bid")
        db.execSQL("DROP TABLE IF EXISTS Message")
        db.execSQL("DROP TABLE IF EXISTS Favorite")

        onCreate(db)
    }

    fun addUser(user: User) {
        val values = ContentValues()
        values.put("userName", user.userName)
        values.put("email", user.email)
        values.put("password", user.password)
        values.put("ImageProfileRef", user.ImageProfileRef)

        val db = this.writableDatabase
        db.insert("User", null, values)

        db.close()
    }

    fun addLot(lot: Lot) {
        val values = ContentValues()
        values.put("userId", lot.ownerId)
        values.put("startPrice", lot.startPrice)
        values.put("description", lot.description)
        values.put("deadline", lot.deadline)
        values.put("category", lot.category)
        values.put("ImageDataRef", lot.ImageDataRef)

        val db = this.writableDatabase
        db.insert("Lot", null, values)

        db.close()
    }

    fun getAllLots(): ArrayList<Lot> {
        val db = this.readableDatabase
        var lot: Lot? = null
        val list: ArrayList<Lot> = arrayListOf<Lot>()
        val cursor = db.rawQuery("SELECT * FROM Lot", null)

        while (cursor.moveToNext()) {
            val ownerID = 13

            val startPriceInd = cursor.getColumnIndex("startPrice")
            val startPrice: Double = cursor.getDouble(startPriceInd)

            val titleInd = cursor.getColumnIndex("title")
            val title = "Стіл “Singer” 2005 року в ідеальному стані"

            val descriptionInd = cursor.getColumnIndex("description")
            val description = cursor.getString(descriptionInd)

            val deadlineInd = cursor.getColumnIndex("deadline")
            val deadline = cursor.getString(deadlineInd)

            val categoryInd = cursor.getColumnIndex("category")
            val category = cursor.getString(categoryInd)

            val imageInd = cursor.getColumnIndex("ImageDataRef")
            val imageRef = cursor.getString(imageInd)

            lot = Lot(null, ownerID, null, startPrice, null, title, description, deadline, category, imageRef)
            list.add(lot)
        }
        return list
    }


    fun addBid(bid: Bid) {
        val values = ContentValues()
        values.put("bidDate", bid.bidDate)
        values.put("bidValue", bid.bidValue) // Fixed typo from "vidValue"
        values.put("userId", bid.costumerId)
        values.put("lotId", bid.lotId)

        val db = this.writableDatabase
        db.insert("Bid", null, values)

        db.close()
    }

    fun addMessage(message: Message) {
        val values = ContentValues()
        values.put("customerId", message.customerId)
        values.put("ownerId", message.ownerId)
        values.put("lotId", message.lotId)
        values.put("message", message.message)

        val db = this.writableDatabase
        db.insert("Message", null, values)

        db.close()
    }

    fun addFavorite(favorite: Favorite) {
        val values = ContentValues()
        values.put("userId", favorite.userId)
        values.put("lotId", favorite.lotId)

        val db = this.writableDatabase
        db.insert("Favorite", null, values)

        db.close()
    }

    fun getUser(email: String, pass: String): Boolean {
        val db = this.readableDatabase
        val result =
            db.rawQuery("SELECT * FROM User WHERE email = '$email' AND password = '$pass'", null)
        return result.moveToFirst()
    }

    fun getUserByEmail(email: String): User? {
        val db = this.readableDatabase
        var user: User? = null

        val cursor = db.rawQuery("SELECT * FROM User WHERE email = ?", arrayOf(email))

        if (cursor.moveToFirst()) {
            val userNameInd = cursor.getColumnIndex("userName")
            val userEmailInd = cursor.getColumnIndex("email")
            val userPasswordInd = cursor.getColumnIndex("password")
            val imageProfileRefInd = cursor.getColumnIndex("ImageProfileRef")

            val userName = cursor.getString(userNameInd)
            val userEmail = cursor.getString(userEmailInd)
            val userPassword = cursor.getString(userPasswordInd)
            val imageProfileRef = cursor.getString(imageProfileRefInd)

            user = User(null, userName, userEmail, userPassword, imageProfileRef)
        }

        cursor.close()
        db.close()

        return user
    }
}
