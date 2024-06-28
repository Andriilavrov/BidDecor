package com.example.biddecor

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.biddecor.model.Bid
import com.example.biddecor.model.Favorite
import com.example.biddecor.model.Lot
import com.example.biddecor.model.Message
import com.example.biddecor.model.User
import java.util.Random

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
                ownerId INTEGER,
                bidId INTEGER DEFAULT NULL,
                startPrice INTEGER,
                buyOutPrice INTEGER DEFAULT NULL,
                title TEXT,
                description TEXT,
                deadline TEXT,
                category TEXT,
                ImageDataRef TEXT,
                FOREIGN KEY (ownerId) REFERENCES User(userId),
                FOREIGN KEY (bidId) REFERENCES Bid(bidId)
            );
            """
        )
        db.execSQL(
            """
            CREATE TABLE Bid (
                bidId INTEGER PRIMARY KEY AUTOINCREMENT,
                bidDate TEXT,
                bidValue INTEGER,
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

        testFillDB()
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
        values.put("ownerId", lot.ownerId)
        values.put("startPrice", lot.startPrice)
        values.put("title", lot.title)
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
        var lot: Lot
        val list: ArrayList<Lot> = arrayListOf()
        val cursor = db.rawQuery("SELECT * FROM Lot", null)

        while (cursor.moveToNext()) {
            val ownerIdInd = cursor.getColumnIndex("ownerId")
            val ownerId: Int = cursor.getInt(ownerIdInd)

            val startPriceInd = cursor.getColumnIndex("startPrice")
            val startPrice: Int = cursor.getInt(startPriceInd)

            val titleInd = cursor.getColumnIndex("title")
            val title = cursor.getString(titleInd)

            val descriptionInd = cursor.getColumnIndex("description")
            val description = cursor.getString(descriptionInd)

            val deadlineInd = cursor.getColumnIndex("deadline")
            val deadline = cursor.getString(deadlineInd)

            val categoryInd = cursor.getColumnIndex("category")
            val category = cursor.getString(categoryInd)

            val imageInd = cursor.getColumnIndex("ImageDataRef")
            val imageRef = cursor.getString(imageInd)

            lot = Lot(
                null,
                ownerId,
                null,
                startPrice,
                null,
                title,
                description,
                deadline,
                category,
                imageRef
            )
            list.add(lot)
        }
        cursor.close()
        db.close()
        return list
    }

    fun addBid(bid: Bid) {
        val values = ContentValues()
        values.put("bidDate", bid.bidDate)
        values.put("bidValue", bid.bidValue)
        values.put("userId", bid.costumerId)
        values.put("lotId", bid.lotId)

        val db = this.writableDatabase
        db.insert("Bid", null, values)

        db.close()
    }

    @SuppressLint("Range")
    fun getBidById(id: Int): Bid? {
        val db = this.readableDatabase
        val cursor =
            db.rawQuery("SELECT 1 FROM Bid WHERE bidId = '$id'", null)

        val bid: Bid? = null
        if (cursor.moveToFirst()) {
            Bid(
                bidId = cursor.getInt( cursor.getColumnIndex("ownerId")),
                bidDate = cursor.getString( cursor.getColumnIndex("bidDate")),
                bidValue = cursor.getInt(cursor.getColumnIndex("bidValue")),
                costumerId = cursor.getInt(cursor.getColumnIndex("costumerId")),
                lotId = cursor.getInt(cursor.getColumnIndex("lotId"))
            )
        }
        return bid
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
            val userIdInd = cursor.getColumnIndex("userId")
            val userNameInd = cursor.getColumnIndex("userName")
            val userEmailInd = cursor.getColumnIndex("email")
            val userPasswordInd = cursor.getColumnIndex("password")
            val imageProfileRefInd = cursor.getColumnIndex("ImageProfileRef")

            val userId = cursor.getInt(userIdInd)
            val userName = cursor.getString(userNameInd)
            val userEmail = cursor.getString(userEmailInd)
            val userPassword = cursor.getString(userPasswordInd)
            val imageProfileRef = cursor.getString(imageProfileRefInd)

            user = User(userId, userName, userEmail, userPassword, imageProfileRef)
        }

        cursor.close()
        db.close()

        return user
    }

    fun insertFavorite(userId: Int?, lotId: Int): Int {
        if (userId == null){
            return -1
        }
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put("userId", userId)
            put("lotId", lotId)
        }
        val insertedId = db.insert("Favorite", null, contentValues)
        db.close()
        return insertedId.toInt()
    }

    fun removeFavorite(favoriteId: Int) {
        val db = writableDatabase
        val sql = "DELETE FROM Favorite WHERE favoriteId = ?"
        val whereArgs = arrayOf(favoriteId.toString())
        val deletedRows = db.execSQL(sql, whereArgs)
        db.close()
    }

    fun testFillDB() {
        addUser(User(null, "TestUser", "test", "123", null))
        addUser(User(null, "John", "john@example.com", "12345678", null))
        addUser(User(null, "Emily", "emily@example.com", "12345678", null))
        addUser(User(null, "User", "user@example.com", "12345678", null))
        addLot(
            Lot(
                null,
                1,
                null,
                2400,
                null,
                "Сучасний журнальний столик",
                "Цей сучасний журнальний столик стане стильним доповненням вашої вітальні. " +
                        "Прозора скляна поверхня створює враження простору і легкості, а міцна металева основа забезпечує стабільність. Ідеальний для розміщення книг, журналів та декоративних елементів. " +
                        "Додайте сучасного шарму до вашого інтер'єру.",
                "04.07.2024",
                "Столи",
                "glass_table"
            )
        )
        addLot(
            Lot(
                null,
                1,
                null,
                1920,
                null,
                "Стіл 'SINGER'",
                "Стан: нове\nНаявність: в наявності\nПризначення: обідній\n" +
                        "Тип: класичний\n\nНовий. Не великі косметичні дефекти , вдавленості при транспортуванні.",
                "12.06.2024",
                "Столи",
                "table"
            )
        )
        addLot(
            Lot(
                null,
                1,
                null,
                870,
                null,
                "Вінтажний дерев'яний стілець",
                "Цей вінтажний дерев'яний стілець додає шарму будь-якому інтер'єру. Виготовлений " +
                        "з високоякісного дерева, він зберіг свій оригінальний блиск і міцність. Ідеально підходить для любителів ретро стилю та цінителів якісних меблів. ",
                "21.07.2024",
                "Стільці",
                "chair"
            )
        )
        addLot(
            Lot(
                null,
                1,
                null,
                1530,
                null,
                "Класичний дубовий обідній стіл",
                "Цей класичний дубовий обідній стіл втілює елегантність і міцність. " +
                        "Виготовлений з високоякісного дуба, він прослужить вам багато років. Простий, але елегантний дизайн дозволяє легко поєднувати його з будь-якими стільцями та декором. Ідеальний вибір для сімейних обідів " +
                        "та святкових вечерь.",
                "16.07.2024",
                "Столи",
                "wood_table"
            )
        )
    }
}
