package com.testosterolapp.unrd.data

import androidx.room.Embedded
import androidx.room.Relation

data class TimelineWithEvents(@Embedded val timeline: Timelines,
                              @Relation(parentColumn = "id_timelines", entityColumn = "id_events")
                              val listOfEvents: List<Events>)