package com.testosterolapp.unrd.data

import androidx.room.Embedded
import androidx.room.Relation

data class CharacterWithImages(@Embedded val characters: Characters,
                               @Relation(parentColumn = "id_characters", entityColumn = "id_image")
                              val listOfImages: List<Image>)
