package com.testosterolapp.unrd;
import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.testosterolapp.unrd.db.Database
import com.testosterolapp.unrd.db.ResultDao
import com.testosterolapp.unrd.db.StatusDao
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.util.concurrent.TimeUnit


abstract class DatabaseTest {
    @Rule

    @JvmField
    val countingTaskExecutorRule = CountingTaskExecutorRule()
    protected lateinit var database: Database
    protected lateinit var resultDao: ResultDao
    protected lateinit var statusDao: StatusDao

    @Before
    @Throws(Exception::class)
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                Database::class.java)
                .build()
        resultDao = database.resultDao()!!
        statusDao = database.statusDao()!!
    }
    @After
    @Throws(Exception::class)
    fun tearDown() {
        database.close()
    }

    fun drain() {
        countingTaskExecutorRule.drainTasks(10, TimeUnit.SECONDS)
    }
}