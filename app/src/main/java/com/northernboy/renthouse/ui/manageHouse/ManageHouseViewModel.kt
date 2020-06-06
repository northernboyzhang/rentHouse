package com.northernboy.renthouse.ui.manageHouse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.northernboy.renthouse.RentRepository
import com.northernboy.renthouse.Utils.getUsrView
import com.northernboy.renthouse.view.HouseView

class ManageHouseViewModel : ViewModel() {
    val houseViewList = liveData {
        emit(getUsrView()?.usrId?.let { RentRepository().getOwnHouse(it) })
    }
}
