package com.northernboy.renthouse.ui.home

import androidx.lifecycle.*
import com.northernboy.renthouse.RentRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val houseViewList = liveData {
        emit(RentRepository().getHouse())
    }

    fun getHouse(){
        viewModelScope.launch {
            RentRepository().getHouse()
        }
    }

    fun reserve(houseId: Int, renterId: Int){
        viewModelScope.launch {
            RentRepository().reserve(houseId, renterId)
        }
    }
}