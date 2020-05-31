package com.northernboy.renthouse.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.northernboy.renthouse.RentRepository
import com.northernboy.renthouse.Utils.getUsrView
import com.northernboy.renthouse.Utils.storeUsrView
import com.northernboy.renthouse.view.UsrView
import kotlinx.coroutines.launch

class UsrViewModel : ViewModel() {
    var usrView = UsrView()
    var isLogin = MutableLiveData(false)
    init {
        usrView = getUsrView()
        if(usrView.identityNo != null && usrView.password != null){
            login(usrView.identityNo!! , usrView.password!!)
        }
    }
    fun login(id: String, password: String) {
        viewModelScope.launch {
            isLogin.value = RentRepository().login(id, password)
        }
    }
    fun logout(){
        storeUsrView(null)
        isLogin.value = false
    }
}