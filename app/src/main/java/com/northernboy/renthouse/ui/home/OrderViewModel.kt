package com.northernboy.renthouse.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.northernboy.renthouse.view.HouseView

class OrderViewModel : ViewModel() {

    var houseView = MutableLiveData(HouseView())

    fun placeOrder(){


    }
}
