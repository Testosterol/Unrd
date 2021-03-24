package com.testosterolapp.unrd.data

import androidx.room.Embedded
import androidx.room.Relation

data class ResultWithListImages(@Embedded val result: Result,
                                @Relation(parentColumn = "id", entityColumn = "id_list_image")
                                val listOfListImages: List<ListImage>)
