package com.testosterolapp.unrd.data

import com.testosterolapp.unrd.data.Data


class Events {

    var type: String? = ""
    var sequence: Long? = null
    var data: Data? = null
    var has_options: Boolean? = null

    constructor(type: String?, sequence: Long?, data: Data?, has_options: Boolean?) {
        this.type = type
        this.sequence = sequence
        this.data = data
        this.has_options = has_options
    }

}