package com.northernboy.renthouse.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.northernboy.renthouse.RentRepository
import com.northernboy.renthouse.Utils.getUsrView
import kotlinx.coroutines.launch
import kotlin.system.measureNanoTime

class RegisterViewModel : ViewModel() {

    fun register(name: String, usrAddress: String, gender:Int, phone: String){
        var usrView = getUsrView()
        usrView?.apply {
            this.name = name
            this.usrAddress = usrAddress
            this.gender = gender
            this.phone = phone
        }
        viewModelScope.launch {
            usrView?.usrId?.let { RentRepository().registerID(name, usrAddress, gender, phone, it) }
        }
    }
}
