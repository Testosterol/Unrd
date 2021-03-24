package com.testosterolapp.unrd.data

import androidx.room.Embedded
import androidx.room.Relation

data class ResultWithPreviewMedia(@Embedded val result: Result,
                                  @Relation(parentColumn = "id", entityColumn = "id_preview_media")
                                  val listPreviewMedia: List<PreviewMedia>)
