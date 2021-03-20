package com.testosterolapp.unrd.util

import android.content.Context
import android.util.Log
import com.testosterolapp.unrd.data.Status
import com.testosterolapp.unrd.db.DaoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.DataOutput

class DbUtil{

    companion object {

        val TAG: String = DbUtil::class.java.simpleName

        fun launchCoroutineForInsertHeadlineIntoDatabase(data: JSONObject, context: Context) {
            CoroutineScope(Dispatchers.IO).launch {
                insertIntoDatabase(data, context)
            }
        }

        private suspend fun insertIntoDatabase(data: JSONObject, context: Context) {
            if (data.toString() != "{}") { // (tl;dr does the same job null check , saves memory)
                insert(data, context)
            } else {
                Log.d(TAG, "data: $data")
            }
        }

        private suspend fun insert(data: JSONObject, context: Context) {
            val daoRepository = DaoRepository(context)

            // status
            val status = Status(data.getJSONObject("status").getInt("code"),
                    data.getJSONObject("status").getString("message"))
            daoRepository.statusDao!!.insertAll(status)

            // result
            val result = data.getJSONObject("result")
            insertResultIntoDatabase(result, context)
        }

        private fun insertResultIntoDatabase(result: JSONObject, context: Context) {
            TODO("Not yet implemented") // to be continued
        }


    }
}