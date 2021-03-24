package com.testosterolapp.unrd.data

import androidx.room.Embedded
import androidx.room.Relation

data class ResultWithTimelines(@Embedded val result: Result,
                               @Relation(parentColumn = "id", entityColumn = "id_timelines")
                               val listOfTimelines: List<Timelines>)
