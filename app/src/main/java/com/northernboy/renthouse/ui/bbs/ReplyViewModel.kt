package com.northernboy.renthouse.ui.bbs

import androidx.lifecycle.*
import com.northernboy.renthouse.RentRepository
import com.northernboy.renthouse.view.PostView

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

}