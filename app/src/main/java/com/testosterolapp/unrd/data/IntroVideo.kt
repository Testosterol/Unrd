package com.testosterolapp.unrd.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(foreignKeys = [ForeignKey(entity = Result::class,
        parentColumns = ["id"],
        childColumns = ["id_fk_result"],
        onDelete = ForeignKey.CASCADE)])
class IntroVideo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id_intro_video: Int = 0

    var id_fk_result: Int? = 0
    var resource_id: Long? = 0
    var resource_fid: String? = null
    var resource_type: String? = null
    var resource_uri: String? = null
    var resource_preset: String? = null
    var resource_processed: Boolean? = false
    var resource_progress: Long? = null


    constructor(id_fk_result: Int, resource_id: Long?, resource_fid: String?, resource_type: String?, resource_uri: String?, resource_preset: String?, resource_processed: Boolean?, resource_progress: Long?) {
        this.id_fk_result = id_fk_result
        this.resource_id = resource_id
        this.resource_fid = resource_fid
        this.resource_type = resource_type
        this.resource_uri = resource_uri
        this.resource_preset = resource_preset
        this.resource_processed = resource_processed
        this.resource_progress = resource_progress
    }
}