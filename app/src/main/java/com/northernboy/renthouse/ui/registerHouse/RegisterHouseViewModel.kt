package com.northernboy.renthouse.ui.registerHouse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.northernboy.renthouse.RentRepository
import com.northernboy.renthouse.Utils.getUsrView
import com.northernboy.renthouse.view.HouseView
import kotlinx.coroutines.launch

class RegisterHouseViewModel : ViewModel() {

    fun registerHouse(houseView:HouseView){
        viewModelScope.launch {
            val usrView = getUsrView()
            if(usrView?.usrId != null
                && usrView.name != null
                && usrView.phone != null){
                houseView.run {
                    RentRepository().registerHouse(usrView.usrId!!, houseAddress!!, houseType!!, houseCapacity!!, houseRent!!)
                }
            }
        }
    }
}
