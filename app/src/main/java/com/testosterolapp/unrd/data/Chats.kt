package com.testosterolapp.unrd.data


class Chats {

    var chat_id: Int? = 0
    var timeline_id: Int? = null
    var chat_name: String? = ""
    var price: Int? = null
    var is_group: Boolean? = null
    var is_locked: Boolean? = null
    var display_name: String? = null
    var owned: Boolean? = null

    constructor(chat_id: Int?, timeline_id: Int?, chat_name: String?, price: Int?,
                is_group: Boolean?, is_locked: Boolean?, display_name: String?, owned: Boolean?) {
        this.chat_id = chat_id
        this.timeline_id = timeline_id
        this.chat_name = chat_name
        this.price = price
        this.is_group = is_group
        this.is_locked = is_locked
        this.display_name = display_name
        this.owned = owned
    }


}