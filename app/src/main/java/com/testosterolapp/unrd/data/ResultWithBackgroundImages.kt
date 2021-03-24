package com.testosterolapp.unrd.data

import androidx.room.Embedded
import androidx.room.Relation

data class ResultWithBackgroundImages(@Embedded val result: Result,
                                      @Relation(parentColumn = "id", entityColumn = "id_background_image")
                                      val listOfBackgroundImage: List<BackgroundImage>)
