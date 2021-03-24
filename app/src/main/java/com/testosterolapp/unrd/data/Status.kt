package com.testosterolapp.unrd.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Status {

    @PrimaryKey(autoGenerate = true)
    var id_status: Int = 0

    var code: Long = 0
    var message: String = ""

    constructor(code: Long, message: String) {
        this.code = code
        this.message = message
    }
}