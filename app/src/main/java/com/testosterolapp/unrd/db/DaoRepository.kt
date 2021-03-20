package com.testosterolapp.unrd.db

import android.content.Context
import com.testosterolapp.unrd.data.Result
import com.testosterolapp.unrd.data.Status

class DaoRepository {

    var resultDao: ResultDao? = null
    var statusDao: StatusDao? = null

    constructor(context: Context) : this() {
        val database = Database.getDatabase(context)
        this.statusDao = database.statusDao()!!
        this.resultDao = database.resultDao()!!
    }

    constructor()

    suspend fun insertResult(result: Result) {
        resultDao?.insertAll(result)
    }

    suspend fun insertStatus(status: Status) {
        statusDao?.insertAll(status)
    }


}