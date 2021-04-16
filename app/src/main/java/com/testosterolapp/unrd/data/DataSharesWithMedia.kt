package com.testosterolapp.unrd.data

import androidx.room.Embedded
import androidx.room.Relation

data class DataSharesWithMedia(@Embedded val dataShares: DataShares,
                               @Relation(parentColumn = "id_data_shares", entityColumn = "id_media")
                               val listOfMedia: List<Media>)
