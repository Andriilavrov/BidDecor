package com.example.biddecor.ui

import android.os.AsyncTask
import java.sql.Connection
import java.sql.DriverManager

class DataBase_test(private val callback: (String?) -> Unit) : AsyncTask<Void, Void, String>() {

    override fun doInBackground(vararg params: Void?): String? {
        val connectionUrl = "jdbc:sqlserver://biddecor.database.windows.net:1433;" +
                "database=BidDecor_v1;" +
                "user=lavrov@biddecor;" +
                "password=Qf@YJYxsrgY87kc;" +
                "encrypt=true;" +
                "trustServerCertificate=false;" +
                "hostNameInCertificate=.database.windows.net;" +
                "loginTimeout=30;"

        return try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
            DriverManager.getConnection(connectionUrl).use { connection ->
                val statement = connection.createStatement()
                val resultSet = statement.executeQuery("SELECT FROM Admin")

                if (resultSet.next()) {
                    resultSet.getString("email")
                } else {
                    "No data found"
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            "Error occurred"
        }
    }

    override fun onPostExecute(result: String?) {
        callback(result)
    }
}