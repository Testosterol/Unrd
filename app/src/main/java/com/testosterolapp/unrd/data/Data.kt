package com.testosterolapp.unrd.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(foreignKeys = [ForeignKey(entity = Events::class,
        parentColumns = arrayOf("id_events"),
        childColumns = arrayOf("id_fk_events"),
        onDelete = ForeignKey.CASCADE)])
class Data {

    @PrimaryKey(autoGenerate = true)
    var id_data: Int = 0

    var id_fk_events: Long? = 0
    var chat_message_id: Long? = null
    var chat_id: Long? = null
    var character_id: Long? = null
    var media_duration: Long? = null
    var content: String? = null
    var url_label: String? = null
    var sequence: Long? = null
    var price: Long? = null
    var is_locked: Boolean? = null
    var has_options: Boolean? = null
    var options_timeout: Long? = null
    var created: String? = null
    var updated: String? = null
    var has_url: Boolean? = null
    var resource_id: Long? = null
    var thumb_resource_id: Long? = null
    var owned: Boolean? = null

    constructor(id_fk_events: Long?, chat_message_id: Long?, chat_id: Long?, character_id: Long?, media_duration: Long?, content: String?, url_label: String?, sequence: Long?, price: Long?, is_locked: Boolean?, has_options: Boolean?, options_timeout: Long?, created: String?, updated: String?, has_url: Boolean?, resource_id: Long?, thumb_resource_id: Long?, owned: Boolean?) {
        this.id_fk_events = id_fk_events
        this.chat_message_id = chat_message_id
        this.chat_id = chat_id
        this.character_id = character_id
        this.media_duration = media_duration
        this.content = content
        this.url_label = url_label
        this.sequence = sequence
        this.price = price
        this.is_locked = is_locked
        this.has_options = has_options
        this.options_timeout = options_timeout
        this.created = created
        this.updated = updated
        this.has_url = has_url
        this.resource_id = resource_id
        this.thumb_resource_id = thumb_resource_id
        this.owned = owned
    }


}