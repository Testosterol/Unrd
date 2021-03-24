package com.testosterolapp.unrd.data

import androidx.room.Embedded
import androidx.room.Relation

data class ResultWithIntroVideo(@Embedded val result: Result,
                                @Relation(parentColumn = "id", entityColumn = "id_intro_video")
                                val listIntroVideo: List<IntroVideo>)

