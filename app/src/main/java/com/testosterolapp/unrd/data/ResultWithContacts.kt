package com.testosterolapp.unrd.data

import androidx.room.Embedded
import androidx.room.Relation

data class ResultWithContacts(@Embedded val result: Result,
                              @Relation(parentColumn = "id", entityColumn = "id_contact")
                              val listOfContacts: List<Contacts>)
