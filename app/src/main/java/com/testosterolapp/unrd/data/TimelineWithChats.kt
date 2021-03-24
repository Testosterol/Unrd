package com.testosterolapp.unrd.data

import androidx.room.Embedded
import androidx.room.Relation

data class TimelineWithChats(@Embedded val timeline: Timelines,
                             @Relation(parentColumn = "id_timelines", entityColumn = "id_chats")
                             val listOfChats: List<Chats>)
