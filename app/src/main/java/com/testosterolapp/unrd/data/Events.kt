package com.testosterolapp.unrd.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Timelines::class,
        parentColumns = arrayOf("id_timelines"),
        childColumns = arrayOf("id_fk_timelines"),
        onDelete = ForeignKey.CASCADE)])
class Events {

    @PrimaryKey(autoGenerate = true)
    var id_events: Int = 0

    var type: String? = ""
    var sequence: Long? = null
    var has_options: Boolean? = null
    var id_fk_timelines: Long? = 0

    constructor(type: String?, sequence: Long?, has_options: Boolean?, id_fk_timelines: Long?) {
        this.type = type
        this.sequence = sequence
        this.has_options = has_options
        this.id_fk_timelines = id_fk_timelines
    }

}