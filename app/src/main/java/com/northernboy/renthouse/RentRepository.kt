package com.northernboy.renthouse

import com.northernboy.renthouse.Utils.rentLog
import com.northernboy.renthouse.Utils.storeUsrView
import com.northernboy.renthouse.view.*
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

    suspend fun getOwnHouse(ownerId: Int): List<HouseView>{
        return get("select * from owner_house_on_view where owner_id = $ownerId"){ re ->
            val newHouseView = HouseView().apply {
                re.run {
                    houseId = getInt("house_id")
                    houseAddress = getString("house_address")
                    houseCapacity = getInt("house_capacity")
                    houseRent = getFloat("house_rent")
                    houseType = getString("house_type")
                    houseStatus = getInt("house_status")
                    houseOnShelve = getInt("house_onShelve")
                }
            }
            newHouseView
        }
    }


    suspend fun getOrder(usrId: Int): List<OrderView>{
        return get("select * from order_view where renter_id = $usrId or owner_id = $usrId"){ re ->
            val newOrderView = OrderView().apply {
                re.run {
                    orderId = getInt("order_id")
                    orderDateTime = getString("order_datetime")
                    orderMonth = getInt("order_month")
                    houseAddress = getString("house_address")
                    houseId = getInt("house_id")
                    houseCapacity = getInt("house_capacity")
                    houseRent = getFloat("house_rent")
                    houseType = getString("house_type")
                    ownerId = getInt("owner_id")
                    ownerName = getString("owner_name")
                    ownerPhone = getString("owner_phone")
                    renterId = getInt("renter_id")
                    renterName = getString("renter_name")
                    renterPhone = getString("renter_phone")
                    isOwner = (usrId == ownerId)
                    total = orderMonth?.times(houseRent!!)?:0f
                }
            }
            newOrderView
        }
    }

    suspend fun getReserve(usrId: Int): List<ReserveView>{
        return get("select * from reserve_view where renter_id = $usrId or owner_id = $usrId"){ re ->
            val newReserveView = ReserveView().apply {
                re.run {
                    reserveId = getInt("reserve_id")
                    renterId = getInt("renter_id")
                    renterName = getString("renter_name")
                    renterPhone = getString("renter_phone")
                    reserveDateTime = getString("reserve_datetime")
                    reserveIsChecked = getInt("reserve_is_checked")
                    houseId = getInt("house_id")
                    houseAddress = getString("house_address")
                    ownerId = getInt("owner_id")
                    ownerName = getString("owner_name")
                    ownerPhone = getString("owner_phone")
                    isOwner = (usrId == ownerId)
                }
            }
            rentLog(newReserveView.toString())
            newReserveView
        }
    }

    suspend fun checkReserve(reserveID :Int){
        Utils.changeMysql("update reserve set isChecked = TRUE where reserve_id = $reserveID")
    }

    suspend fun updateHouse(houseId: Int, houseAddress: String, houseType: String, houseCapacity: Int, houseRent: Float, status: Boolean, onShelve: Boolean){
        Utils.changeMysql(
            "update house set house_address = '$houseAddress', type='$houseType', capacity = $houseCapacity, rent= $houseRent, status = $status, onShelve = $onShelve where house_id = $houseId")
    }

    suspend fun deleteHouse(houseId: Int){

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
        val usrViewList = get("select * from usr where identity_no = '$id'") { re ->
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
            rentLog(usrView.toString())
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

    suspend fun reply(content: String, replyerId: Int, postId: Int){
        rentLog("insert into reply value(null, '$content', now(), $replyerId, $postId)")
        Utils.changeMysql("insert into reply value(null, '$content', now(), $replyerId, $postId)")
    }

    suspend fun placeOrder(month: Int, renterId: Int, houseId: Int ) {
        Utils.changeMysql("insert into rent_order value(null, $renterId, $houseId, $month, now())")
    }

    suspend fun reserve(houseId: Int, renterId: Int){
        Utils.changeMysql("insert into reserve value(null, $renterId, $houseId, now(), FALSE)")
    }

    suspend fun registerID(name: String, usrAddress: String, gender: Int, phone: String, usrId: Int){
        Utils.changeMysql("Update usr set name='$name', usr_address='$usrAddress', gender= $gender, phone='$phone' where usr_id='$usrId'")
    }

    suspend fun registerHouse(ownerId: Int, houseAddress: String, type: String, capacity: Int, rent: Float){
        Utils.changeMysql("insert into manage_house value(null,$ownerId, '$houseAddress', '$type', $capacity, $rent, TRUE, TRUE)")
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