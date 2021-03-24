package com.testosterolapp.unrd;

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.testosterolapp.unrd.data.Status
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class CoroutineAndDatabaseTests : DatabaseTest() {

    @After
    fun teardown() {
        // At the end of all tests, query executor should be idle.
        countingTaskExecutorRule.drainTasks(500, TimeUnit.MILLISECONDS)
        assert(countingTaskExecutorRule.isIdle)
    }

    @Test
    fun testStatusRemovalDelete() {
        runBlocking {
            val status = Status(0, "Test4")
            statusDao.insert(status)
            assertEquals(statusDao.getAll()!!.size, 1)

            database.clearAllTables()
            assertEquals(statusDao.getAll(), listOf<Status>())
        }
    }

    @Test
    fun testStatusMessageSuspend() = runBlocking {
        val status = Status(0, "testName")
        statusDao.insert(status)
        assertEquals(statusDao.getAll()!![0]!!.message, ((status.message)))
    }

    @Test
    fun getAllStatuses() {
        runBlocking {
            val status = Status(0, "Test2")
            val status2 = Status(1, "Test4")
            val status3 = Status(2, "Test4")
            statusDao.insertAll(status, status2, status3)
            val statuses = statusDao.getAll()
            assertEquals(statuses!!.size, 3)
        }
    }

    @Test
    fun isTableEmptySuspend() {
        runBlocking {
            val isTableEmpty = resultDao.isTableEmpty()
            assertEquals(isTableEmpty, 0)
        }
    }

    @Test
    fun isTableNotEmptySuspend() {
        runBlocking {
            val status = Status(0, "Test2")
            statusDao.insertAll(status)
            val isTableEmpty = statusDao.isTableEmpty()
            assertEquals(isTableEmpty, 1)
        }
    }
}