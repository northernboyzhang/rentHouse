package com.northernboy.renthouse.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.northernboy.renthouse.RentRepository
import kotlinx.coroutines.launch

class UsrViewModel : ViewModel() {
    var isLogin = MutableLiveData(false)

    fun login(id: String, password: String) {
        viewModelScope.launch {
            isLogin.value = RentRepository().login(id, password)
        }
    }
}