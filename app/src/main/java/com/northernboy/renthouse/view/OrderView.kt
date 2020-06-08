package com.northernboy.renthouse.view

class OrderView: BaseView(){
    var orderId: Int? = null
    var orderDateTime: String? = null
    var orderMonth : Int? = null
    var renterId: Int? = null
    var renterName: String? = null
    var renterPhone: String? = null
    var houseId: Int? = null
    var houseAddress: String? = null
    var houseType: String? = null
    var houseCapacity: Int? = null
    var houseRent: Float? = null
    var ownerId : Int? = null
    var ownerName: String? = null
    var ownerPhone: String? = null
    var isOwner: Boolean = false//是租客还是房主
    var total: Float? = null
}
