package com.testosterolapp.unrd.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.testosterolapp.unrd.data.Status

@Dao
interface StatusDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg status: Status)

}