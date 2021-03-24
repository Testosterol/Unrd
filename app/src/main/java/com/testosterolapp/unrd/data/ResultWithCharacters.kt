package com.testosterolapp.unrd.data

import androidx.room.Embedded
import androidx.room.Relation

data class ResultWithCharacters(@Embedded val result: Result,
                                @Relation(parentColumn = "id", entityColumn = "id_characters")
                                val listOfCharacters: List<Characters>)
