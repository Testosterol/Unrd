package com.testosterolapp.unrd.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(foreignKeys = [ForeignKey(entity = Result::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id_fk_result"),
        onDelete = ForeignKey.CASCADE)])
class Characters {

    @PrimaryKey(autoGenerate = true)
    var id_characters: Int = 0

    var id_fk_result: Long? = 0
    var character_id: Long? = 0
    var name: String? = null
    var is_main: Boolean? = false


    constructor(id_fk_result: Long, character_id: Long?, name: String?, is_main: Boolean?) {
        this.id_fk_result = id_fk_result
        this.character_id = character_id
        this.name = name
        this.is_main = is_main
    }
}



