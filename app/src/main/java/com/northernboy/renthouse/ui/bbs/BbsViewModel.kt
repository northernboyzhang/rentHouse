package com.northernboy.renthouse.ui.bbs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.northernboy.renthouse.RentRepository

class BbsViewModel : ViewModel() {

    val postViewList = liveData {
        emit(RentRepository().getPost())
    }

}