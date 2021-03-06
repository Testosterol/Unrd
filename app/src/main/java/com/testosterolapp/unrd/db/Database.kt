package com.testosterolapp.unrd.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.testosterolapp.unrd.data.*

@androidx.room.Database(entities = [Result::class, Status::class,
    BackgroundImage::class, Characters::class, Chats::class, Contacts::class,
    Data::class, Events::class, Image::class, IntroVideo::class, ListImage::class,
    PreviewMedia::class, PurchasedItems::class, Timelines::class, DataShares::class,
    Media::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun statusDao(): StatusDao?
    abstract fun resultDao(): ResultDao?


    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: Database? = null

        fun getDatabase(context: Context): Database {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        Database::class.java,
                        "db_unrd")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}