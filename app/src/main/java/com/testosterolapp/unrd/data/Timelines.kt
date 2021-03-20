package com.testosterolapp.unrd.data

import androidx.room.Embedded
import com.testosterolapp.unrd.data.Chats
import com.testosterolapp.unrd.data.Events

class Timelines {

    var timeline_id: Int? = 0
    var timeline_name: String? = null
    var is_primary: Boolean? = false
    var is_terminal: Boolean? = false
    var created: String? = null
    var updated: String? = null

    @Embedded
    var chats: Chats? = null

    @Embedded
    var events: Events? = null


    constructor(timeline_id: Int?, timeline_name: String?, is_primary: Boolean?, is_terminal: Boolean?, created: String?, updated: String?, chats: Chats?, events: Events?) {
        this.timeline_id = timeline_id
        this.timeline_name = timeline_name
        this.is_primary = is_primary
        this.is_terminal = is_terminal
        this.created = created
        this.updated = updated
        this.chats = chats
        this.events = events
    }

}