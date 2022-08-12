package com.udacity.asteroidradar.database.iodt

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabasePictureOfDay::class], version = 1)
abstract class PictureOfDayDatabase : RoomDatabase() {
    abstract val pictureDao : PictureOfDayDao
}

private lateinit var INSTANCE: PictureOfDayDatabase

fun getPictureDatabase(context: Context): PictureOfDayDatabase {
    synchronized(PictureOfDayDatabase::class.java){
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                PictureOfDayDatabase::class.java,
                "pictures")
                .build()
        }
    }

    return INSTANCE
}