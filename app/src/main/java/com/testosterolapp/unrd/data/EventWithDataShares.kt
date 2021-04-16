package com.testosterolapp.unrd.data

import androidx.room.Embedded
import androidx.room.Relation

data class EventWithDataShares(@Embedded val events: Events,
                         @Relation(parentColumn = "id_events", entityColumn = "id_data_shares")
                         val listOfDataShares: List<DataShares>)
