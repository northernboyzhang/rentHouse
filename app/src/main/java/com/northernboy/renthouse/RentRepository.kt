package com.northernboy.renthouse

import android.app.Application
import android.content.Context.MODE_PRIVATE
import com.northernboy.renthouse.view.HouseView
import com.northernboy.renthouse.view.PostView
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
            val re = Utils.getMysql("insert into usr values(null, null, null, '$id', null, null, '$_password')")
            re.close()
            return login(id, _password)
        }
        if (usrViewList.size == 1 && usrViewList[0].password == _password) {
            val sharedPre = Application().getSharedPreferences("Usr", MODE_PRIVATE)
            sharedPre.edit().putString("Usr", usrViewList[0].toString()).apply()
            return true
        } else if (usrViewList.size == 1 && usrViewList[0].password != _password) {
            return false
        } else {
            return false
        }
    }

    private suspend fun <T> get(query: String, buildItem: (re: ResultSet)-> T): List<T> = withContext(Dispatchers.IO) {
        val re = Utils.getMysql(query)
        val list = mutableListOf<T>()
        while (re.next()){
             list.add(buildItem(re))
        }
        re.close()
        list
    }
}