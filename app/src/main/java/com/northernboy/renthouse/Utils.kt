package com.northernboy.renthouse
import android.util.Log
import java.sql.DriverManager
import java.sql.ResultSet


object Utils {
    init {
        Class.forName("com.mysql.jdbc.Driver")
    }
    fun getMysql(query: String): ResultSet{
        val conn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/mydb?useSSL=false&serverTimezone=UTC","usr","5711")
        val stmt = conn.createStatement()
        stmt.queryTimeout = 3000
        return stmt.executeQuery(query)
    }

    fun rentLog(msg: String){
        Log.d("Rent", msg)
    }
}