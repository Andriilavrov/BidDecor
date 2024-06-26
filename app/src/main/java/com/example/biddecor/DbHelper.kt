package com.example.biddecor

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
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
                ImageProfileRef TEXT
            );
            """)
        db!!.execSQL("""
            CREATE TABLE Lot (
                lotId INT PRIMARY KEY AUTOINCREMENT,
                userId INT,
                startPrice DECIMAL(10, 2),
                buyOutPrice DECIMAL(10, 2),
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
            CREATE TABLE Chat (
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
                userId INT PRIMARY KEY AUTOINCREMENT,
                lotId INT,
                FOREIGN KEY (userId) REFERENCES Users(userId),
                FOREIGN KEY (lotId) REFERENCES Lot(lotId)
            );
            """)
        db!!.execSQL("""
            CREATE TABLE Admin (
                adminId INT PRIMARY KEY AUTOINCREMENT,
                email TEXT,
                password TEXT
            );
            """)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS User")
        db!!.execSQL("DROP TABLE IF EXISTS Lot")
        db!!.execSQL("DROP TABLE IF EXISTS Bid")
        db!!.execSQL("DROP TABLE IF EXISTS Chat")
        db!!.execSQL("DROP TABLE IF EXISTS Favorite")
        db!!.execSQL("DROP TABLE IF EXISTS Admin")

        onCreate(db)
    }

    fun addUser(user: User) {
        val values = ContentValues()
        values.put("login", user.userName)
        values.put("email", user.userEmal)
        values.put("pass", user.password)

        val db = this.writableDatabase
        db.insert("users", null, values)

        db.close()
    }
}