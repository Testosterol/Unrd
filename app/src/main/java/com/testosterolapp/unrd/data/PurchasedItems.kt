package com.testosterolapp.unrd.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(foreignKeys = [ForeignKey(entity = Result::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id_fk_result"),
        onDelete = ForeignKey.CASCADE)])
class PurchasedItems {

    @PrimaryKey(autoGenerate = true)
    var id_purchased_items: Int = 0

    var exchange_type: String? = null
    var exchange_key: Long? = null
    var id_fk_result: Long? = 0


    constructor(exchange_type: String?, exchange_key: Long?, id_fk_result: Long?) {
        this.exchange_type = exchange_type
        this.exchange_key = exchange_key
        this.id_fk_result = id_fk_result
    }
}