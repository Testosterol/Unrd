package com.testosterolapp.unrd.data

class PurchasedItems {

    var exchange_type: String? = null
    var exchange_key: Long? = null

    constructor(exchange_type: String?, exchange_key: Long?) {
        this.exchange_type = exchange_type
        this.exchange_key = exchange_key
    }
}