package com.testosterolapp.unrd.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Result::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id_fk_result"),
        onDelete = ForeignKey.CASCADE)])
class Timelines {

    @PrimaryKey(autoGenerate = true)
    var id_timelines: Int = 0

    var id_fk_result: Long? = 0
    var timeline_id: Long? = 0
    var name: String? = null
    var is_primary: Boolean? = false
    var is_terminal: Boolean? = false
    var created: String? = null
    var updated: String? = null


    constructor(id_fk_result: Long?, timeline_id: Long?, name: String?, is_primary: Boolean?,
                is_terminal: Boolean?, created: String?, updated: String?) {
        this.id_fk_result = id_fk_result
        this.timeline_id = timeline_id
        this.name = name
        this.is_primary = is_primary
        this.is_terminal = is_terminal
        this.created = created
        this.updated = updated
    }


}