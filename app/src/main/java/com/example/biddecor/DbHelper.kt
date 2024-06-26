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

class DbHelper(val context: Context, val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "biddecor", factory, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("""
            CREATE TABLE User (
                userId INT PRIMARY KEY AUTOINCREMENT,
                userName TEXT,
                email TEXT,
                password TEXT,
                ImageProfileRef TEXT DEFAULT NULL
            );
            """)
        db!!.execSQL("""
            CREATE TABLE Lot (
                lotId INT PRIMARY KEY AUTOINCREMENT,
                userId INT,
                startPrice DECIMAL(10, 2),
                buyOutPrice DECIMAL(10, 2) DEFAULT NULL,
                description TEXT,
                deadline DATETIME,
                category TEXT,
                ImageDataRef TEXT,
                FOREIGN KEY (userId) REFERENCES Users(userId)
            );
            """)
        db!!.execSQL("""
            CREATE TABLE Bid (
                bidId INT PRIMARY KEY AUTOINCREMENT,
                bidDate DATETIME,
                bidValue DECIMAL(10, 2),
                userId INT,
                lotId INT,
                FOREIGN KEY (userId) REFERENCES Users(userId),
                FOREIGN KEY (lotId) REFERENCES Lot(lotId)
            );
            """)
        db!!.execSQL("""
            CREATE TABLE Message (
                messageId INT PRIMARY KEY AUTOINCREMENT,
                customerId INT PRIMARY KEY AUTOINCREMENT,
                ownerId INT,
                lotId INT,
                message TEXT,
                FOREIGN KEY (customerId) REFERENCES Users(userId),
                FOREIGN KEY (ownerId) REFERENCES Users(userId),
                FOREIGN KEY (lotId) REFERENCES Lot(lotId)
            );
            """)
        db!!.execSQL("""
            CREATE TABLE Favorite (
                favoriteId INT PRIMARY KEY AUTOINCREMENT,
                userId INT,
                lotId INT,
                FOREIGN KEY (userId) REFERENCES Users(userId),
                FOREIGN KEY (lotId) REFERENCES Lot(lotId)
            );
            """)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS User")
        db!!.execSQL("DROP TABLE IF EXISTS Lot")
        db!!.execSQL("DROP TABLE IF EXISTS Bid")
        db!!.execSQL("DROP TABLE IF EXISTS Message")
        db!!.execSQL("DROP TABLE IF EXISTS Favorite")

        onCreate(db)
    }

    fun addUser(user: User) {
        val values = ContentValues()
        values.put("userName", user.userName)
        values.put("email", user.email)
        values.put("password", user.password)

        val db = this.writableDatabase
        db.insert("User", null, values)

        db.close()
    }

    fun addLot(lot: Lot) {
        val values = ContentValues()
        values.put("userId", lot.userId)
        values.put("startPrice", lot.startPrice)
        values.put("description", lot.description)
        values.put("deadline", lot.deadline)
        values.put("category", lot.category)
        values.put("ImageDataRef", lot.ImageDataRef)

        val db = this.writableDatabase
        db.insert("Lot", null, values)

        db.close()
    }

    fun addBid(bid: Bid) {
        val values = ContentValues()
        values.put("bidDate", bid.bidDate)
        values.put("vidValue", bid.vidValue)
        values.put("userId", bid.userId)
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
}