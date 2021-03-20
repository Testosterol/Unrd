package com.testosterolapp.unrd.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Status {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    var code: Int = 0
    var message: String = ""

    constructor(code: Int, message: String) {
        this.code = code
        this.message = message
    }
}