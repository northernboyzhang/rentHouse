package com.northernboy.renthouse
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.google.gson.Gson
import com.northernboy.renthouse.view.UsrView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement


object Utils {
    init {
        Class.forName("com.mysql.jdbc.Driver")
    }

    suspend fun getMysql(query: String): ResultSet = withContext(Dispatchers.IO){
       mysql(query){stmt, query ->
           stmt.executeQuery(query)
       }
    }

    suspend fun changeMysql(query: String): Boolean = withContext(Dispatchers.IO){
        mysql(query){stmt, query ->
            stmt.execute(query)
        }
    }

    fun rentLog(msg: String){
        Log.d("Rent", msg)
    }

    private suspend fun<T> mysql(query: String, execute:(stmt: Statement, query: String)->T): T = withContext(Dispatchers.IO){
        val conn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/mydb?useSSL=false&serverTimezone=UTC","usr","5711")
        val stmt = conn.createStatement()
        stmt.queryTimeout = 3000
        execute(stmt, query)
    }

    fun getUsrView(): UsrView?{
        return Gson().fromJson(RentHouseApplication.context.getSharedPreferences("Usr", MODE_PRIVATE).getString("Usr", null), UsrView::class.java)
    }

    fun storeUsrView(usrView: UsrView?){
        RentHouseApplication.context.getSharedPreferences("Usr", MODE_PRIVATE).edit().putString("Usr", Gson().toJson(usrView)).apply()
    }

    fun centerToast(context: Context, info: String){
        Toast.makeText(context, info, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.CENTER, 0, 0)
        }.show()
    }
}