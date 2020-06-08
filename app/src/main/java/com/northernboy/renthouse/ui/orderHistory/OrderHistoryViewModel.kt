package com.northernboy.renthouse.ui.orderHistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.northernboy.renthouse.RentRepository
import com.northernboy.renthouse.Utils.getUsrView
import com.northernboy.renthouse.view.OrderView

class OrderHistoryViewModel : ViewModel() {

    val orderViewList = liveData {
        getUsrView()?.usrId?.let { RentRepository().getOrder(it) }?.let { emit(it) }
    }
}
