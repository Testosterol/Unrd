package com.testosterolapp.unrd.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(foreignKeys = [ForeignKey(entity = Timelines::class,
        parentColumns = arrayOf("id_timelines"),
        childColumns = arrayOf("id_fk_timeline"),
        onDelete = ForeignKey.CASCADE)])
class Chats {


    var id_chats: Int = 0

    var id_fk_timeline: Long? = 0

    @PrimaryKey
    var chat_id: Long? = 0
    var timeline_id: Long? = null
    var name: String? = ""
    var price: Long? = null
    var is_group: Boolean? = null
    var is_locked: Boolean? = null
    var display_name: String? = null
    var owned: Boolean? = null



    constructor(id_fk_timeline: Long?, chat_id: Long?, timeline_id: Long?, name: String?,
                price: Long?, is_group: Boolean?, is_locked: Boolean?, display_name: String?,
                owned: Boolean?) {
        this.id_fk_timeline = id_fk_timeline
        this.chat_id = chat_id
        this.timeline_id = timeline_id
        this.name = name
        this.price = price
        this.is_group = is_group
        this.is_locked = is_locked
        this.display_name = display_name
        this.owned = owned
    }
}