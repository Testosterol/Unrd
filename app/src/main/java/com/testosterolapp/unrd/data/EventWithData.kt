package com.testosterolapp.unrd.data

import androidx.room.Embedded
import androidx.room.Relation

data class EventWithData(@Embedded val events: Events,
                         @Relation(parentColumn = "id_events", entityColumn = "id_data")
                         val listOfData: List<Data>)
