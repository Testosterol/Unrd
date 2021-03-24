package com.testosterolapp.unrd.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.testosterolapp.unrd.data.Status

@Dao
interface StatusDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(status: Status): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg status: Status)

    @Query("SELECT * FROM Status")
    fun getAll(): List<Status?>?

    @Query("SELECT COUNT(*) FROM status")
    suspend fun isTableEmpty(): Long
}