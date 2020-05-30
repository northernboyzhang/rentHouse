package com.northernboy.renthouse.view

import com.google.gson.Gson

open class BaseView {
    override fun toString(): String {
        return Gson().toJson(this)
    }
}