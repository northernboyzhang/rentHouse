package com.northernboy.renthouse

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class RentHouseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}