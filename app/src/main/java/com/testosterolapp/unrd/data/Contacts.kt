package com.testosterolapp.unrd.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(foreignKeys = [ForeignKey(entity = Result::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id_fk_result"),
        onDelete = ForeignKey.CASCADE)])
class Contacts {

    @PrimaryKey(autoGenerate = true)
    var id_contact: Int = 0

    var contact_id: Int = 0
    var id_fk_result: Long? = 0

    constructor(contact_id: Int, id_fk_result: Long) {
        this.contact_id = contact_id
        this.id_fk_result = id_fk_result
    }

}