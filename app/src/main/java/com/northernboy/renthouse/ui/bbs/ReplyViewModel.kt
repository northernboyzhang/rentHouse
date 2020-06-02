package com.northernboy.renthouse.ui.bbs

import androidx.lifecycle.*
import com.northernboy.renthouse.RentRepository
import com.northernboy.renthouse.Utils.centerToast
import com.northernboy.renthouse.Utils.getUsrView
import com.northernboy.renthouse.view.PostView
import kotlinx.coroutines.launch

class ReplyViewModel: ViewModel(){

    var postView = MutableLiveData(PostView())


    val replyViewList = Transformations.switchMap(postView){
        liveData {
            if(it.postId != null){
                emit(RentRepository().getReply(it.postId!!))
            }else
                return@liveData
        }
    }

    fun reply(content: String, postId: Int){
        val usrView = getUsrView()
        if(usrView?.usrId != null){
            viewModelScope.launch {
                RentRepository().reply(content, usrView.usrId!!, postId)
            }
        }

    }

}