package com.testosterolapp.unrd.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Events::class,
        parentColumns = arrayOf("id_events"),
        childColumns = arrayOf("id_fk_events"),
        onDelete = ForeignKey.CASCADE)])
class DataShares {


    @PrimaryKey(autoGenerate = true)
    var id_data_shares: Int = 0

    var id_fk_events: Long? = 0

    var character_share_id: Long? = null
    var character_id: Long? = null
    var media_duration: Long? = null
    var stream_path: String? = null
    var sequence: Long? = null
    var duration: Long? = null
    var price: Long? = null
    var is_locked: Boolean? = null
    var is_live: Boolean? = null
    var is_public: Boolean? = null
    var created: String? = null
    var updated: String? = null
    var resource_id: Long? = null


    constructor(id_fk_events: Long?, character_share_id: Long?, character_id: Long?,
                media_duration: Long?, stream_path: String?, sequence: Long?,
                duration: Long?, price: Long?, is_locked: Boolean?, is_live: Boolean?,
                is_public: Boolean?, created: String?, updated: String?, resource_id: Long?) {
        this.id_fk_events = id_fk_events
        this.character_share_id = character_share_id
        this.character_id = character_id
        this.media_duration = media_duration
        this.stream_path = stream_path
        this.sequence = sequence
        this.duration = duration
        this.price = price
        this.is_locked = is_locked
        this.is_live = is_live
        this.is_public = is_public
        this.created = created
        this.updated = updated
        this.resource_id = resource_id
    }

}