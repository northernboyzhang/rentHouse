package com.northernboy.renthouse.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.northernboy.lib1.Text
import com.northernboy.renthouse.RentRepository
import com.northernboy.renthouse.view.HouseView
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {

    var houseView = MutableLiveData(HouseView())
    var month = 0
    val a = Text()

    fun placeOrder(renterId: Int){
        viewModelScope.launch {
            if(month != 0){
                houseView.value?.houseId?.let {
                    RentRepository().placeOrder(month, renterId, it) }
            }
        }
    }

    fun calculate(): Float{
        return month.times(houseView.value?.houseRent?:0f)
    }
}
