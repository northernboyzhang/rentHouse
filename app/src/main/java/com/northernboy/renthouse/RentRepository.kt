package com.northernboy.renthouse

import android.app.Application
import android.content.Context.MODE_PRIVATE
import com.northernboy.renthouse.Utils.storeUsrView
import com.northernboy.renthouse.view.HouseView
import com.northernboy.renthouse.view.PostView
import com.northernboy.renthouse.view.ReplyView
import com.northernboy.renthouse.view.UsrView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.ResultSet

class RentRepository {

    suspend fun getHouse() : List<HouseView> {
        return get("select * from usr_browse_house_view") {  re ->
            val newHouseView = HouseView().apply {
                re.run {
                    houseId = getInt("house_id")
                    houseAddress = getString("house_address")
                    houseCapacity = getInt("house_capacity")
                    houseRent = getFloat("house_rent")
                    houseType = getString("house_type")
                }
            }
            newHouseView
        }
    }

    suspend fun getPost(): List<PostView>{
        return get("select * from usr_browse_bbs_view"){re ->
            val newPostView = PostView().apply {
                re.run {
                    postAuthorId = getInt("post_author_id")
                    postDate = getString("post_date")
                    postTheme = getString("post_theme")
                    postId = getInt("post_id")
                    postAuthorName = getString("post_author_name")
                }
            }
            newPostView
        }
    }

    suspend fun login(id: String, _password: String): Boolean {
        val usrViewList = get<UsrView>("select * from usr where identity_no = '$id'") { re ->
            val usrView = UsrView().apply {
                re.run {
                    usrId = re.getInt("usr_id")
                    usrAddress = re.getString("usr_address")
                    name = re.getString("name")
                    identityNo = re.getString("identity_no")
                    gender = re.getInt("gender")
                    phone = re.getString("phone")
                    password = re.getString("password")
                }
            }
            usrView
        }
        if (usrViewList.isEmpty()) {
            Utils.changeMysql("insert into usr value(null, null, null, '$id', null, null, '$_password')")
            return login(id, _password)
        }
        return if (usrViewList.size == 1 && usrViewList[0].password == _password) {
            storeUsrView(usrViewList[0])
            true
        } else if (usrViewList.size == 1 && usrViewList[0].password != _password) {
            false
        } else {
            false
        }
    }

    suspend fun getReply(postId: Int): List<ReplyView> {
        return get("select * from usr_reply_bbs_view where reply_post_id = $postId"){re ->
            val newReplyView = ReplyView().apply {
                re.run {
                    replyId = getInt("reply_id")
                    replyPostId = getInt("reply_post_id")
                    replyerName = getString("replyer_name")
                    replyerId = getInt("replyer_id")
                    replyTime = getString("reply_time")
                    replyContent = getString("reply_content")
                }
            }
            newReplyView
        }
    }

    suspend fun placeOrder(month: Int, renter_id: Int, house_id: Int ) {
        Utils.changeMysql("insert into rent_order value(null, $renter_id, $house_id, $month, now())")
    }

    private suspend fun <T> get(query: String, buildItem: (re: ResultSet)-> T): List<T> {
        val re = Utils.getMysql(query)
        val list = mutableListOf<T>()
        while (re.next()){
             list.add(buildItem(re))
        }
        re.close()
        return list
    }
}