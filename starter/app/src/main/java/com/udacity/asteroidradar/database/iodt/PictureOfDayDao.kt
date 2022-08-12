package com.udacity.asteroidradar.database.iodt

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Constants

@Dao
interface PictureOfDayDao {

    @Query("SELECT * FROM ${Constants.PICTURE_OF_DAY_TABLE} WHERE mediaType = 'image'")
    fun getPictureOfDay () : LiveData<DatabasePictureOfDay>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPictureOfDay(picture : DatabasePictureOfDay)
}