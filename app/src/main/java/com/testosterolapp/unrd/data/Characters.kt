package com.testosterolapp.unrd.data

import androidx.room.Embedded


class Characters {

    var character_id: Int? = 0
    var character_name: String? = null
    var is_main: Boolean? = false

    @Embedded
    var image: Image? = null

    constructor(character_id: Int?, character_name: String?, is_main: Boolean?, image: Image?) {
        this.character_id = character_id
        this.character_name = character_name
        this.is_main = is_main
        this.image = image
    }
}



