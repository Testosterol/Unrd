package com.testosterolapp.unrd.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.testosterolapp.unrd.data.Result

@Dao
interface ResultDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg result: Result)

}