package com.udacity.asteroidradar.database.iodt

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.Constants

@Entity(tableName = Constants.PICTURE_OF_DAY_TABLE)
data class DatabasePictureOfDay (
    @PrimaryKey val mediaType : String,
    val url : String,
    val title : String)