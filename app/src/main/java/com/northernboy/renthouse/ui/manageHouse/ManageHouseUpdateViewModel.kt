package com.northernboy.renthouse.ui.manageHouse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.northernboy.renthouse.RentRepository
import com.northernboy.renthouse.view.HouseView
import kotlinx.coroutines.launch

class ManageHouseUpdateViewModel : ViewModel() {

    var _houseView = MutableLiveData(HouseView())
    val houseView: LiveData<HouseView> = _houseView

    fun updateHouse(){
        viewModelScope.launch {
            _houseView.value?.let {
                RentRepository().updateHouse(
                    it.houseId!!,
                    it.houseAddress!!,
                    it.houseType!!,
                    it.houseCapacity!!,
                    it.houseRent!!,
                    it.houseStatus!! == 1,
                    it.houseOnShelve!! == 1
                )
            }
        }
    }

    fun setHouseView(houseView: HouseView){
        _houseView.value = houseView
    }
}
