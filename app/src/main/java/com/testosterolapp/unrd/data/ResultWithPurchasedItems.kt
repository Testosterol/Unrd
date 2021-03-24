package com.testosterolapp.unrd.data

import androidx.room.Embedded
import androidx.room.Relation

data class ResultWithPurchasedItems(@Embedded val result: Result,
                                    @Relation(parentColumn = "id", entityColumn = "id_purchased_items")
                                    val listPurchasedItems: List<PurchasedItems>)