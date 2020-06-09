package com.northernboy.renthouse.ui.reserve

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.northernboy.renthouse.RentRepository
import com.northernboy.renthouse.Utils.getUsrView
import com.northernboy.renthouse.view.ReserveView

class ReserveViewModel : ViewModel() {
    val reserveViewList = liveData {
        getUsrView()?.usrId?.let {  emit(RentRepository().getReserve(it)) }
    }
}
